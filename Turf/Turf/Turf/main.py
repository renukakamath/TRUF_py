from flask import Flask
from public import public
from admin import admin
from turf import turf
from api import api

app=Flask(__name__)

app.secret_key="secretkey"

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix="/admin")
app.register_blueprint(turf,url_prefix="/turf")
app.register_blueprint(api,url_prefix="/api")

app.run(debug=True,port=5345,host="0.0.0.0")