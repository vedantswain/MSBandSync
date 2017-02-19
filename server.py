from flask import Flask
from flask import request
from flask import json
from flask import Response

app = Flask(__name__)

@app.route('/')
def index():
	return 'This where the data at'

@app.route('/data', methods = ['POST'])
def api_data():
	if request.headers['Content-Type'] == 'application/json':
		js = json.dumps(request.json)
		print "JSON Message: " + json.dumps(request.json)
		resp = Response(js, status=200, mimetype='application/json')
		return resp


if __name__ == '__main__':
	app.run()