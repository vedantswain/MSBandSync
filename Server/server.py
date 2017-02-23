from flask import Flask
from flask import request
from flask import json
from flask import jsonify
from flask import Response
from flask import render_template

import json
import time
import datetime
import random
import sys


app = Flask(__name__)

dataList = []

# To relax the number of entries in sent in 1 Ajax query
displayFreq = 5      
dataNumber = 0
lastGet = time.time()*1000

@app.route('/')
def index():
	return render_template('index.html')

@app.route('/data', methods = ['GET','POST'])
def api_data():
	global dataList
	global displayFreq
	global dataNumber
	global lastGet

	if request.method == 'GET':
		# a = -5
		# b = 5
		# result = {"id":"Wrist","timestamp": time.time()*1000, "x": random.uniform(a, b), "y": random.uniform(a, b), "z": random.uniform(a, b)}
		# responseData = jsonify(result)
		responseData = json.dumps(dataList)
		# print(str(len(dataList))+" at "+str(time.time()))
		# print (time.time()*1000) - lastGet
		# lastGet = time.time()*1000
		dataList = []
		return responseData
	elif request.method == 'POST' and request.headers['Content-Type'] == 'application/json':
		
		allJSON = request.json["data"]

		for jo in allJSON:
			js = json.dumps(jo)
			# print "JSON Message: " + js

			timeString  = datetime.datetime.fromtimestamp(jo["timestamp"]/1000).strftime('%H:%M:%S')
			# print timeString

			resp = Response(js, status=200, mimetype='application/json')
			# if dataNumber % displayFreq == 0:
			dataList.append(js);
			if len(dataList)>16:
				dataList.pop(0)
			# dataNumber+=1
		return resp


if __name__ == '__main__':
	app.run()