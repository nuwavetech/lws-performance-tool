#!/bin/bash

# Set the main class as a variable
MAIN_CLASS="com.nuwavetech.sample.lws_performance_tool.LWSPerformanceTool"

# Set the build directory
BUILD_DIR="./build"

# Set the output jar name
OUTPUT_JAR="LWSPerformanceTool.jar"

# Clean the build directory
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

# Compile the source files
echo "Compiling source files..."
javac -cp ".:lib/*:$BUILD_DIR" -d "$BUILD_DIR" src/main/java/com/nuwavetech/sample/lws_performance_tool/*.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed. Exiting."
    exit 1
fi

# Create a temporary directory for jar contents
TMP_DIR="$BUILD_DIR/tmp"
mkdir -p "$TMP_DIR"

# Copy compiled classes to the temporary directory
cp -r "$BUILD_DIR/com" "$TMP_DIR/"

# Extract dependency jars and copy their contents to the temporary directory
echo "Extracting dependencies..."
for jar in lib/*.jar; do
    unzip -qo "$jar" -d "$TMP_DIR"
done

# Remove any existing META-INF/MANIFEST.MF
rm -f "$TMP_DIR/META-INF/MANIFEST.MF"

# Create the manifest file
echo "Creating MANIFEST.MF..."
mkdir -p "$TMP_DIR/META-INF"
echo "Manifest-Version: 1.0" > "$TMP_DIR/META-INF/MANIFEST.MF"
echo "Main-Class: $MAIN_CLASS" >> "$TMP_DIR/META-INF/MANIFEST.MF"

# Create the fat jar
echo "Creating fat jar..."
jar -cvfm "$BUILD_DIR/$OUTPUT_JAR" "$TMP_DIR/META-INF/MANIFEST.MF" -C "$TMP_DIR" .

# Clean up temporary directory
rm -rf "$TMP_DIR"

echo "Fat jar created: $BUILD_DIR/$OUTPUT_JAR"
echo "You can run it with: java -jar $BUILD_DIR/$OUTPUT_JAR <threads> <simple|complex> <ipm size> <seconds>"