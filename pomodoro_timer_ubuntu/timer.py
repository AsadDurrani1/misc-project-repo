from playsound import playsound
import time
import os
FILENAME = "Napalm Death - You Suffer.wav"

class Timer:
	def __init__(self, minutes=0, seconds=0):
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
		minutes = self.minutes
		while self.minutes > 0 or self.seconds > 0:
			time.sleep(1)
			self.count_down()
		#	os.system('clear')
			print(self)
		playsound(FILENAME)
		self.minutes = 25 if minutes == 5 else 5
		self.begin_timer()

	def set_time(self, minutes, seconds):
		self.minutes = minutes
		self.seconds = seconds
        
if __name__ == "__main__":
	T = Timer(25)
	T.begin_timer()
