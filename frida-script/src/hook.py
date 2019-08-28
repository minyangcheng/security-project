import json
import os
import sys

import frida

logPath = os.path.join(os.path.abspath('.'), 'hook.log')


def write_log(str):
    with open(logPath, 'a') as f:
        f.write(str + '\n', )


def on_message(message, data):
    if message['type'] == 'send':
        temp = json.loads(message['payload'])
        print("\033[34m%s : \033[0m%s" % (temp['head'], temp['message']))
        write_log(temp['head'] + ' : ' + temp['message'].replace('[0;32m', '').replace('[0m ', '').replace('[0;33m',
                                                                                                           '').replace(
            '[0m', ''))
    else:
        print(message)


scriptNames = ['qu_tou_tiao.js', 'tool.js']
scriptCodes = []
for path in scriptNames:
    scriptPath = os.path.join(os.path.abspath('./src/script'), path)
    print('[*] script path = %s' % scriptPath)
    with open(scriptPath, 'r') as f:
        code = f.read()
        scriptCodes.append(code)

completeCode = '\n'.join(scriptCodes)
packageName = 'com.min.app.sample'
device = frida.get_usb_device()

print('[*] Running Hook')
runType = 1
if runType == 1:
    process = device.attach(packageName)
    script = process.create_script(completeCode)
    script.on('message', on_message)
    script.load()
    sys.stdin.read()
else:
    pid = device.spawn([packageName])
    process = device.attach(pid)
    script = process.create_script(completeCode)
    script.on('message', on_message)
    script.load()
    device.resume(pid)
    sys.stdin.read()
