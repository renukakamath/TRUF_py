from flask import Blueprint,request,render_template,flash,session,redirect,url_for
from database import*
import uuid

turf=Blueprint('turf',__name__)

@turf.route("/turf_home")
def turf_home():
	if not session.get("lid") is None:
		return render_template("turf_home.html")
	else:
		return rediect(url_for("public.login"))

@turf.route("/turf_add_images",methods=['get','post'])
def turf_add_images():
	if not session.get("lid") is None:
		data={}
		if 'sm1' in request.form:
			img1=request.files['img1']
			path1="static/"+str(uuid.uuid4())+img1.filename
			img1.save(path1)
			q="INSERT INTO images(turf_id,image,date_time,image_type) VALUES('%s','%s',now(),'game')"%(session['tid'],path1)
			insert(q)
			flash("Image Added")
			return redirect(url_for("turf.turf_add_images"))
		if 'sm2' in request.form:
			img2=request.files['img2']
			path2="static/"+str(uuid.uuid4())+img2.filename
			img2.save(path2)
			q="INSERT INTO images(turf_id,image,date_time,image_type) VALUES('%s','%s',now(),'infrastructure')"%(session['tid'],path2)
			insert(q)
			flash("Image Added")
			return redirect(url_for("turf.turf_add_images"))
		if 'sm3' in request.form:
			img3=request.files['img3']
			path3="static/"+str(uuid.uuid4())+img3.filename
			img3.save(path3)
			q="INSERT INTO images(turf_id,image,date_time,image_type) VALUES('%s','%s',now(),'facility')"%(session['tid'],path3)
			insert(q)
			flash("Image Added")
			return redirect(url_for("turf.turf_add_images"))
		q="select * from images where turf_id='%s'"%(session['tid'])
		data['view_img']=select(q)
		return render_template("turf_add_images.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_chat_with_users")
def turf_chat_with_users():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,concat(first_name,' ',last_name) as name FROM `users` WHERE login_id IN (SELECT IF(`sender_id`='%s',`receiver_id`,`sender_id`) FROM chat WHERE sender_id='%s' OR receiver_id='%s')"%(session['lid'],session['lid'],session['lid'])
		print(q)
		res=select(q)
		data['msgs']=res
		return render_template('turf_chat_with_users.html',data=data)
	else:
		return redirect(url_for("public.login"))
	return render_template("turf_chat_with_users.html")

@turf.route("/turf_manage_facility",methods=['get','post'])
def turf_manage_facility():
	if not session.get("lid") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			fid=request.args['fid']
		else:
			action=None

		if action=='update':
			q="SELECT * FROM facilities WHERE facility_id='%s'"%(fid)
			data['updates']=select(q)
			
		if action=='remove':
			q="DELETE FROM facilities WHERE facility_id='%s'"%(fid)
			res=delete(q)
			flash("Removed")
			return redirect(url_for('turf.turf_manage_facility'))

		if 'submit_update' in request.form:
			fac=request.form['fac']
			des=request.form['des']
			image=request.files['image']
			val=image.filename
			if val!="":
				path="static/facility_images/"+str(uuid.uuid4())+image.filename
				image.save(path)
				q="UPDATE facilities set facility='%s',description='%s',image='%s',date=CURDATE() WHERE facility_id='%s'"%(fac,des,path,fid)
				update(q)
			else:
				q="UPDATE facilities set facility='%s',description='%s',date=CURDATE() WHERE facility_id='%s'"%(fac,des,fid)
				update(q)
			flash("Changes Saved")
			return redirect(url_for('turf.turf_manage_facility'))

		if 'submit' in request.form:
			fac=request.form['fac']
			des=request.form['des']
			image=request.files['image']
			path="static/facility_images/"+str(uuid.uuid4())+image.filename
			image.save(path)
			q="INSERT INTO facilities(turf_id,facility,description,image,DATE) VALUES('%s','%s','%s','%s',CURDATE())"%(session['tid'],fac,des,path)
			insert(q)
			flash("Values Inserted!")
			return redirect(url_for('turf.turf_manage_facility'))
		q="SELECT * FROM facilities  where turf_id='%s'"%(session['tid'])
		data['fac']=select(q)
		return render_template("turf_manage_facility.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_manage_slots_and_amount",methods=['get','post'])
def turf_manage_slots_and_amount():
	if not session.get("lid") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			sid=request.args['sid']
		else:
			action=None

		if action=='update':
			q="SELECT * FROM slots WHERE slot_id='%s'"%(sid)
			data['updates']=select(q)
			
		if action=='remove':
			q="DELETE FROM slots WHERE slot_id='%s'"%(sid)
			res=delete(q)
			flash("Removed")
			return redirect(url_for('turf.turf_manage_slots_and_amount'))

		if 'submit_update' in request.form:
			day=request.form['day']
			ftime=request.form['ftime']
			ttime=request.form['ttime']
			amt=request.form['amt']
			q="UPDATE slots set day='%s',from_time='%s',to_time='%s',amount='%s',date_time=NOW() WHERE slot_id='%s'"%(day,ftime,ttime,amt,sid)
			update(q)
			flash("Changes Saved")
			return redirect(url_for('turf.turf_manage_slots_and_amount'))

		if 'submit' in request.form:
			day=request.form['day']
			ftime=request.form['ftime']
			ttime=request.form['ttime']
			amt=request.form['amt']
			q="INSERT INTO `slots`(`slot_id`, `turf_id`, `day`, `from_time`, `to_time`, `amount`, `date_time`) VALUES (NULL,'%s','%s','%s','%s','%s',NOW())"%(session['tid'],day,ftime,ttime,amt)
			insert(q)
			flash("Values Inserted!")
			return redirect(url_for('turf.turf_manage_slots_and_amount'))

		q="SELECT * FROM slots where turf_id='%s'"%(session['tid'])
		data['slots']=select(q)
		return render_template("turf_manage_slots_and_amount.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_view_admin_commision")
def turf_view_admin_commision():
	if not session.get("lid") is None:
		data={}
		q="select * from commision"
		data['commision']=select(q)
		return render_template("turf_view_admin_commision.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_view_bookings",methods=['get','post'])
def turf_view_bookings():
	if not session.get("lid") is None:
		data={}
		q="select *,concat(first_name,' ',last_name)as name,bookings.date_time as dt from bookings inner join users using(user_id) inner join slots using(slot_id) where turf_id='%s'"%(session['tid'])
		data['booking']=select(q)
		return render_template("turf_view_bookings.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_view_feedback")
def turf_view_feedback():
	if not session.get("lid") is None:
		data={}
		q="select *,concat(first_name,' ',last_name)as name from feedbacks inner join users using(user_id) where turf_id='%s'"%(session['tid'])
		data['feedback']=select(q)
		return render_template("turf_view_feedback.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_view_match_scheduling_by_user")
def turf_view_match_scheduling_by_user():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,concat(first_name,' ',last_name)as name FROM matches INNER JOIN bookings USING(book_id) INNER JOIN users USING(user_id) inner join slots using(slot_id) where turf_id='%s'"%(session['tid'])
		data['match']=select(q)
		return render_template("turf_view_match_scheduling_by_user.html",data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_view_reviewers_and_rating")
def turf_view_reviewers_and_rating():
	if not session.get("lid") is None:
		data={}
		q="select *,concat(first_name,' ',last_name)as name from ratings inner join users using(user_id) where turf_id='%s' order by rate"%(session['tid'])
		data['reviews']=select(q)
		return render_template('turf_view_reviewers_and_rating.html',data=data)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_match_detail",methods=['get','post'])
def turf_match_detail():
	if not session.get("lid") is None:
		data={}
		mid=request.args['match_id']
		id=request.args['id']
		q="SELECT *,concat(first_name,' ',last_name)as name,bookings.date_time as dt FROM matches INNER JOIN bookings USING(book_id) INNER JOIN users ON users.user_id=matches.`opp_user_id` inner join slots using(slot_id) where match_id='%s'"%(mid)
		data['match']=select(q)
		q="select *,concat(first_name,' ',last_name)as name from users where user_id='%s' order by date_time"%(id)
		data['user']=select(q)
		return render_template("turf_match_detail.html",data=data,id=id)
	else:
		return redirect(url_for("public.login"))

@turf.route("/turf_chat_message",methods=['get','post'])
def turf_chat_message():
	if not session.get("lid") is None:
		data={}
		print("$$$$$$$$$$$$$")
		print(session['lid'])
		cid=request.args['cid']

		qry="select *,concat(first_name,' ',last_name) as name from users where login_id='%s'"%(cid)
		result=select(qry)
		data['name']=result

		if 'submit' in request.form:
			message=request.form['msg']

			q2="INSERT INTO chat(sender_id,sender_type,receiver_id,receiver_type,message,`date_time`)VALUES('%s','turf','%s','user','%s',NOW())"%(session['lid'],cid,message)
			insert(q2)
			return redirect(url_for('turf.turf_chat_message',cid=cid))
		q="SELECT * FROM chat WHERE (`sender_id`='%s' AND `receiver_id`='%s') OR (`sender_id`='%s' AND `receiver_id`='%s')"%(session['lid'],cid,cid,session['lid'])
		print(q)
		res=select(q)
		data['msg']=res
		return render_template('turf_chat_message.html',data=data)
	else:
		return redirecct(url_for("public.login"))

@turf.route("/turf_gallery")
def turf_gallery():
	if not session.get("lid") is None:
		data={}
		image_type=request.args['image_type']
		q="SELECT * FROM images WHERE image_type='%s'"%(image_type)
		data['img']=select(q)
		return render_template("turf_gallery.html",data=data)
	else:
		return redirect(url_for("public.login"))


@turf.route("/turf_view_payment",methods=['get','post'])
def turf_view_payment():
	if not session.get("lid") is None:
		data={}
		book_id=request.args['book_id']
		q="SELECT *,concat(first_name,' ',last_name)as name FROM payments inner join bookings using(book_id) inner join users using(user_id) WHERE book_id='%s'"%(book_id)
		data['pay']=select(q)
		return render_template("turf_view_payment.html",data=data)
	else:
		return redirect(url_for("public.login"))





@turf.route("/turf_manage_coaches",methods=['get','post'])
def turf_manage_coaches():
	if not session.get("lid") is None:
		data={}
		tid=session['tid']
		if 'action' in request.args:
			action=request.args['action']
			sid=request.args['sid']
		else:
			action=None

		if action=='update':
			q="SELECT * FROM coaches WHERE login_id='%s'"%(sid)
			data['updates']=select(q)
			
		if action=='remove':
			q="DELETE FROM coaches WHERE login_id='%s'"%(sid)
			res=delete(q)
			q="DELETE FROM login WHERE login_id='%s'"%(sid)
			res=delete(q)
			flash("Removed")
			return redirect(url_for('turf.turf_manage_coaches'))

		if 'submit_update' in request.form:
			fname=request.form['fn']
			lname=request.form['ln']
			place=request.form['pl']
			phone=request.form['ph']
			email=request.form['em']
			age=request.form['age']
			gen=request.form['gen'] 
			des=request.form['des']
			st=request.form['st'] 
			fa=request.form['fa']
			q="UPDATE coaches set fname='%s',lname='%s',place='%s',phone='%s',email='%s',age='%s',gender='%s',description='%s',sports_type='%s',fee_amount='%s' WHERE login_id='%s'"%(fname,lname,place,phone,email,age,gen,des,st,fa,sid)
			update(q)
			flash("Changes Saved")
			return redirect(url_for('turf.turf_manage_coaches'))

		if 'submit' in request.form:
			fname=request.form['fn']
			lname=request.form['ln']
			place=request.form['pl']
			phone=request.form['ph']
			email=request.form['em']
			age=request.form['age']
			gen=request.form['gen'] 
			des=request.form['des']
			st=request.form['st'] 
			fa=request.form['fa']
			uname=request.form['un']
			passs=request.form['pwd']
			q="insert into login values(null,'%s','%s','coach')"%(uname,passs)
			id=insert(q)
			q="INSERT INTO `coaches` VALUES (NULL,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id,tid,fname,lname,place,phone,email,age,gen,des,st,fa)
			insert(q)
			flash("Registration under process.")
			return redirect(url_for('turf.turf_manage_coaches'))

		q="SELECT * FROM coaches where turf_id='%s'"%(session['tid'])
		res=select(q)
		data['cc']=res
		print(res)
		return render_template("turf_manage_coaches.html",data=data)
	else:
		return redirect(url_for("public.login"))




@turf.route("/turf_schedule_coaching_time",methods=['get','post'])
def turf_schedule_coaching_time():
	if not session.get("lid") is None:
		data={}
		tid=session['tid']
		id=request.args['id']
		data['id']=id
		if 'action' in request.args:
			action=request.args['action']
			sid=request.args['sid']
		else:
			action=None

		if action=='update':
			q="SELECT * FROM schedule_time WHERE schedule_id='%s'"%(sid)
			data['updates']=select(q)
			
		if action=='remove':
			q="DELETE FROM schedule_time WHERE schedule_id='%s'"%(sid)
			res=delete(q)
			flash("Removed")
			return redirect(url_for('turf.turf_schedule_coaching_time',id=id))

		if 'submit_update' in request.form:
			dt=request.form['dt']
			tm=request.form['tm']
			q="UPDATE schedule_time set `date`='%s',`time`='%s' WHERE schedule_id='%s'"%(dt,tm,sid)
			update(q)
			flash("Changes Saved")
			return redirect(url_for('turf.turf_schedule_coaching_time',id=id))

		if 'submit' in request.form:
			dt=request.form['dt']
			tm=request.form['tm']
			q="INSERT INTO `schedule_time` VALUES (NULL,'%s','%s','%s','%s')"%(tid,id,dt,tm)
			insert(q)
			flash("Added Successfully.")
			return redirect(url_for('turf.turf_schedule_coaching_time',id=id))

		q="SELECT * FROM schedule_time where turf_id='%s'"%(session['tid'])
		res=select(q)
		data['schedule']=res
		print(res)
		return render_template("turf_schedule_coaching_time.html",data=data)
	else:
		return redirect(url_for("public.login"))