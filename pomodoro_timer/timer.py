import winsound
import time

class Timer:
    def __init__(self, minutes: int = 0, seconds: int = 0):
        self.minutes = minutes
        self.seconds = seconds

    def __repr__(self):
        m = str(self.minutes) if self.minutes >= 10 else '0' + str(self.minutes)
        s = str(self.seconds) if self.seconds >= 10 else '0' + str(self.seconds)
        return m + ":" + s

    def count_down(self):
        if self.minutes == self.seconds == 0:
            return
        elif self.seconds == 0:
            self.seconds = 59
            self.minutes -= 1
        else:
            self.seconds -= 1
    
    def begin_timer(self):
        while self.minutes > 0 or self.seconds > 0:
            print(self)
            time.sleep(1)
            self.count_down()
        print(self)
        winsound.PlaySound("Napalm Death - You Suffer", winsound.SND_ASYNC)

    def set_time(self, minutes: int = 0, seconds: int = 0):
        self.minutes = minutes
        self.seconds = seconds
        
if __name__ == "__main__":
    T = Timer(1)
    T.begin_timer()
