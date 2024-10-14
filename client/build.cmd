@echo off
setlocal enabledelayedexpansion

REM Set the project root directory
set "PROJECT_ROOT=%~dp0"

REM Set the source directory
set "SRC_DIR=%PROJECT_ROOT%src\main\java"

REM Set the build directory
set "BUILD_DIR=%PROJECT_ROOT%build"

REM Set the lib directory
set "LIB_DIR=%PROJECT_ROOT%lib"

REM Create build directory if it doesn't exist
if not exist "%BUILD_DIR%" mkdir "%BUILD_DIR%"

REM Compile the Java files
echo Compiling Java files...
set "CLASSPATH=%LIB_DIR%\*"

REM Create a temporary file to store all .java file paths
set "JAVA_FILES=%TEMP%\java_files.txt"
dir /s /b "%SRC_DIR%\*.java" > "%JAVA_FILES%"

REM Compile all Java files at once
javac -d "%BUILD_DIR%" -cp "%CLASSPATH%" @"%JAVA_FILES%"

if %ERRORLEVEL% neq 0 (
    echo Compilation failed.
    del "%JAVA_FILES%"
    exit /b 1
)

REM Delete the temporary file
del "%JAVA_FILES%"

REM Create the JAR file
echo Creating JAR file...
jar cvfe "%BUILD_DIR%\PerformanceTester.jar" com.nuwavetech.sample.lws_performance_tool.PerformanceTester -C "%BUILD_DIR%" .

if %ERRORLEVEL% neq 0 (
    echo JAR creation failed.
    exit /b 1
)

echo Build completed successfully.
echo JAR file created at %BUILD_DIR%\PerformanceTester.jar

endlocal