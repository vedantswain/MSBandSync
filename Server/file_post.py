import fileinput
import sys
import csv

# positions = ["a", "b", "x"]
positions = ["x"]

if __name__ == '__main__':

	for position in positions:
		accelfilename = "data/"+"accel"+"_pos_"+position+".csv"
		gyrofilename = "data/"+"gyro"+"_pos_"+position+".csv"

		accel_row_count = 0
		gyro_row_count = 0

		with open(accelfilename, "r") as f:
			reader = csv.reader(f, delimiter=",")
			data = list(reader)
			accel_row_count = len(data)

		with open(gyrofilename, "r") as f:
			reader = csv.reader(f, delimiter=",")
			data = list(reader)
			gyro_row_count = len(data)

		diff_row_count = accel_row_count - gyro_row_count	# difference between rows of two files

		f = fileinput.input(accelfilename, inplace=True) # sys.stdout is redirected to the file
		print next(f), # write header as first line

		w = csv.writer(sys.stdout)
		row_counter = 0
		for row in csv.reader(f):
			if row_counter >= diff_row_count:
				w.writerow(row)
			row_counter = row_counter+1
