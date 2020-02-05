import sys
import io
import fileinput
import random

def spongebobify(s: str) -> str:
    ret = ""
    for char in s:
        ret += char.lower() if random.randint(0,1) == 0 else char.upper()
    return ret

if sys.argv[1:]:
    file = open(sys.argv[1], 'r')
    for line in file:
        print(spongebobify(line))
else:
    for line in fileinput.input():
        print(spongebobify(line))
 
