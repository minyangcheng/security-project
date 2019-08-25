#include <string.h>
#include "common.h"
#include "logUtil.h"

char *getUserName() {
    static char *realUserName = "minych";
    return realUserName;
}

void getPassword(char *password) {
    char *temp = "123";
    strcpy(password, temp);
}

int loginCheck(char *name, char *password) {
    char *realUserName = getUserName();
    char realPassword[40];
    getPassword(realPassword);

    LOGD("realUserName=%s , realPassword=%s", realUserName, realPassword);

    if (strcmp(name, realUserName) != 0) {
        return 0;
    }
    if (strcmp(password, realPassword) != 0) {
        return 0;
    }

    return 1;
}



