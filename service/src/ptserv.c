
// Copyright (c) 2024 NuWave Technologies, Inc. All rights reserved.

#pragma nolist

#include <cextdecs>
#include <tal.h>
#include <zsysc>

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stddef.h>
#include <stdarg.h>
#include <errno.h>

#include "pt.h"

#pragma list

#define MAX_MSG 2097152
#define OMIT /**/

int main(int argc, char** argv) {

  short rc;
  short receive_filenum = 0;
  int done = 0;
  __int32_t count_read;
  char* message = NULL;
  int open_count = 0;

  message = malloc(MAX_MSG);

  rc = FILE_OPEN_("$RECEIVE", 8, &receive_filenum, OMIT, OMIT, OMIT, 1);
  if (rc != 0) {
    printf("Error opening $RECEIVE: %hd, %s\n", rc, strerror(rc));
    return 0;
  }

  while (!done) {
    rc = READUPDATEXL(receive_filenum, (char*)message, MAX_MSG, &count_read);

    if (rc == 6) {
      switch (*((short*)message)) {
      case ZSYS_VAL_SMSG_OPEN:
        open_count++;
        break;

      case ZSYS_VAL_SMSG_CLOSE:
        open_count--;
        break;

      default:
        break;
      }

      REPLYX();

      if (open_count == 0) {
        done = 1;
      }

      continue;
    }

    if (rc != 0) {
      printf("Error reading $RECEIVE: %hd, %s\n", rc, strerror(rc));
      break;
    }

    rc = REPLYXL(message, count_read);

    if (rc != 0) /* file system error */
    {
      printf("Error replying: %hd, %s\n", rc, strerror(rc));
      break;
    }
  }

  if (receive_filenum != 0) {
    FILE_CLOSE_(receive_filenum);
  }

  if (message != NULL) {
    free(message);
  }

  return 0;
}
