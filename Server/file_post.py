import fileinput
import sys
import csv

variations = ["activities_scap","activities_chest"]
exercises = ["flex_stat","flex_move","raise_stat","raise_move","walk"]
positions = ["a", "b", "x"]
# positions = ["x"]
# positions = ["a"]

def  row_count(accelfilename,gyrofilename):
	accel_row_count = 0
	gyro_row_count = 0

	try:
		with open(accelfilename, "r") as f:
			reader = csv.reader(f, delimiter=",")
			data = list(reader)
			accel_row_count = len(data)

		with open(gyrofilename, "r") as f:
			reader = csv.reader(f, delimiter=",")
			data = list(reader)
			gyro_row_count = len(data)

			diff_row_count = accel_row_count - gyro_row_count	# difference between rows of two files

			print accelfilename+", "+gyrofilename+": "+str(diff_row_count)
			
			if diff_row_count > 0:
				f = fileinput.input(accelfilename, inplace=True) # sys.stdout is redirected to the file
				file_count = accel_row_count
			else:
				# return
				diff_row_count = diff_row_count * -1
				f = fileinput.input(gyrofilename, inplace=True)
				file_count = gyro_row_count
			
			print next(f), # write header as first line

			w = csv.writer(sys.stdout)
			row_counter = 0
			for row in csv.reader(f):
				if row_counter < file_count-diff_row_count-1:
					w.writerow(row)
				row_counter = row_counter+1
	
	except IOError:
		print "Error: File does not appear to exist."
      	return


if __name__ == '__main__':

	for variation in variations:
		print "\n########### "+variation
		for exercise in exercises:
			print "\n##### "+exercise
			for position in positions:
				accelfilename = "data/"+variation+"/"+exercise+"/"+"accel"+"_pos_"+position+".csv"
				gyrofilename = "data/"+variation+"/"+exercise+"/"+"gyro"+"_pos_"+position+".csv"
				row_count(accelfilename,gyrofilename)

		
