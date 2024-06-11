from flask import Blueprint,render_template,url_for,request,redirect,session,flash
from database import*

public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template("home.html")

@public.route('/login',methods=['get','post'])
def login():
	session.clear()
	if 'submit' in request.form:
		uname=request.form['uname']
		passs=request.form['passs']
		q="select * from login where username='%s' and password='%s'" %(uname,passs)
		res=select(q)
		if res:
			session['lid']=res[0]['login_id']
			if res[0]['usertype']=="admin":
				flash("Logging in")			
				return redirect(url_for("admin.admin_home"))
			elif res[0]['usertype']=="turf":
				q="select * from turfs where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['tid']=res1[0]['turf_id']
					print(session['tid'])
					flash("Logging in")
					return redirect(url_for("turf.turf_home"))


			elif res[0]['usertype']=="coach":
				q="select * from coaches where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['cid']=res1[0]['coach_id']
					session['tid']=res1[0]['turf_id']
					flash("Logging in")
					return redirect(url_for("coach.coach_home"))


			else:
				flash("Registration Under Process")
		flash("You are Not Registered")
	return render_template("login.html")

@public.route("/register",methods=['get','post'])
def register():
	if 'submit' in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		turf_place=request.form['turf_place']
		land_mark=request.form['land_mark']
		pincode=request.form['pincode']
		lat=request.form['lat']
		longt=request.form['long']
		phone=request.form['phone']
		email=request.form['email']
		uname=request.form['uname']
		passs=request.form['passs']
		
		q="insert into login values(null,'%s','%s','NA')"%(uname,passs)
		id=insert(q)
		q="INSERT INTO `turfs`(`turf_id`, `login_id`, `owner_first_name`, `owner_last_name`, `turf_place`, `landmark`, `pincode`, `latitude`, `longitude`, `phone`, `email`) VALUES (NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,turf_place,land_mark,pincode,lat,longt,phone,email)
		insert(q)
		flash("Registration under process.")
		return redirect(url_for("public.login"))
	return render_template("register.html")