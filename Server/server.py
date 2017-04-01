import os
import csv
import json
import time
import datetime

from flask import Flask
from flask import request
from flask import json
from flask import Response
from flask import render_template


app = Flask(__name__)

dataList = []
dataDict = {}

# To relax the number of entries in sent in 1 Ajax query
displayFreq = 5      
dataNumber = 0
lastGet = time.time()*1000

@app.route('/Wrist')
def wrist_index():
	return render_template('index1.html')

@app.route('/Elbow')
def elbow_index():
	return render_template('index2.html')

@app.route('/data/<sensortype>/<position>', methods = ['GET','POST'])
def api_data(sensortype, position):
	global dataDict
	global dataList
	global displayFreq
	global dataNumber
	global lastGet

	if not position in dataDict:
		dataDict[position] = []

	if request.method == 'GET':
		# a = -5
		# b = 5
		# result = {"id":"Wrist","timestamp": time.time()*1000, "x": random.uniform(a, b), "y": random.uniform(a, b), "z": random.uniform(a, b)}
		# responseData = jsonify(result)
		responseData = json.dumps(dataDict[position])
		# print(str(len(dataList))+" at "+str(time.time()))
		# print (time.time()*1000) - lastGet
		# lastGet = time.time()*1000
		dataList = []
		return responseData
	elif request.method == 'POST' and request.headers['Content-Type'] == 'application/json':
		
		allJSON = request.json["data"]

		write_file(sensortype, position, allJSON)

		for jo in allJSON:
			js = json.dumps(jo)
			# print "JSON Message: " + js
			timeString = datetime.datetime.fromtimestamp(jo["timestamp"]/1000).strftime('%H:%M:%S')
			# print timeString
			resp = Response(js, status=200, mimetype='application/json')
			# if dataNumber % displayFreq == 0:
			dataDict[position].append(js)
			if len(dataDict[position]) > 16:
				dataDict[position].pop(0)
			# dataNumber+=1
		return resp

def write_file(sensortype, position, jsArr):

	HEADER = "position"+","+"x"+","+"y"+","+"z"+","+"timestamp"+"\n"
	filename = "data/"+sensortype+"_"+position+".csv"

	try:
		file = open(filename, 'r')
	except IOError:
		file = open(filename, 'w')

	if os.stat(filename).st_size == 0:
		with open(filename, "wb") as fo:
			fo.write(HEADER)

	# print(write_str)
	with open(filename, "a") as fo:
		writer = csv.writer(fo, quoting=csv.QUOTE_NONNUMERIC)
		for jo in jsArr:
			js = json.dumps(jo)
			js_data = json.loads(js)
			writer.writerow((js_data['id'], js_data['x'], js_data['y'], js_data['z'], js_data['timestamp']))

if __name__ == '__main__':

	app.run()



