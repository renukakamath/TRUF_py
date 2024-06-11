from flask import Blueprint,render_template,request,redirect,url_for,flash,session
from database import *

admin=Blueprint('admin',__name__)

@admin.route("/admin_home")
def admin_home():
	if session.get("lid"):
		return render_template("admin_home.html")
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_manage_turf")
def admin_manage_turf():
	if not session.get("lid") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			tid=request.args['tid']
		else:
			action=None
		if action=='accept':
			q="update login set usertype='turf' where login_id='%s'"%(tid)
			update(q)
			flash("You accepted Turf")
		if action=='reject':
			q="update login set usertype='rejected' where login_id='%s'"%(tid)
			update(q)
			flash("Rejected")
		q="SELECT *,CONCAT(owner_first_name,' ',owner_last_name) AS turf_name FROM turfs INNER JOIN login USING(login_id)"
		data['turf_details']=select(q)
		return render_template("admin_manage_turf.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_set_commision",methods=['get','post'])
def admin_set_commision():
	if not session.get("lid") is None:
		data={}
		if 'action' in request.args:
			action=request.args['action']
			cid=request.args['cid']
		else:
			action=None

		if action=='update':
			q="SELECT * FROM commision WHERE commision_id='%s'"%(cid)
			data['updates']=select(q)
			
		if action=='remove':
			q="DELETE FROM commision WHERE commision_id='%s'"%(cid)
			res=delete(q)
			flash("Removed")
			return redirect(url_for('admin.admin_set_commision'))

		if 'submit_update' in request.form:
			perc=request.form['perc']
			q="UPDATE commision set percentage='%s',date_time=NOW() WHERE commision_id='%s'"%(perc,cid)
			update(q)
			flash("Changes Saved")
			return redirect(url_for('admin.admin_set_commision'))

		if 'submit' in request.form:
			perc=request.form['perc']
			q="INSERT INTO commision(percentage,date_time)VALUES('%s',now())"%(perc)
			insert(q)
			flash("Commision Set!")
			return redirect(url_for('admin.admin_set_commision'))
		q="select * from commision"
		data['commision']=select(q)
		return render_template("admin_set_commision.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_view_complaints_and_reply",methods=['get','post'])
def admin_view_complaints_and_reply():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(first_name,' ',last_name) AS username FROM complaints INNER JOIN users USING(user_id)"
		data['msgs']=select(q)
		i=1
		for row in data['msgs']:
			if 'submit'+str(i) in request.form:
				reply=request.form['reply'+str(i)]
				print(reply)
				id=request.form['complaint_id'+str(i)]
				print(id)
				q="update complaints set reply='%s',date_time=NOW() where complaint_id='%s'"%(reply,id)
				update(q)
				flash("Replied")
				return redirect(url_for("admin.admin_view_complaints_and_reply"))
			i=i+1
		return render_template("admin_view_complaints_and_reply.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_view_registered_users")
def admin_view_registered_users():
	if not session.get("lid") is None:
		data={}
		q="select *,concat(first_name,' ',last_name)as name from users"
		data['user_details']=select(q)
		return render_template("admin_view_registered_users.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_view_users_ratings_and_reviews")
def admin_view_users_ratings_and_reviews():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(owner_first_name,' ',owner_last_name) AS turf_name FROM turfs"
		data['ratings']=select(q)
		return render_template("admin_view_users_ratings_and_reviews.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_turf_rate")
def admin_turf_rate():
	if not session.get("lid") is None:
		data={}
		tid=request.args['tid']
		q="select *,CONCAT(owner_first_name,' ',owner_last_name) AS turf_name from turfs where turf_id='%s'"%(tid)
		data['tid']=select(q)
		q="SELECT *,CONCAT(first_name,' ',last_name) AS name FROM ratings inner join users using(user_id) where turf_id='%s' order by rate"%(tid)
		data['ratings']=select(q)
		return render_template("admin_turf_rate.html",data=data)
	else:
		return redirect(url_for("public.login"))


@admin.route("/admin_view_slots_amount")
def admin_view_slots_amount():
	if not session.get("lid") is None:
		data={}
		q="SELECT *,CONCAT(owner_first_name,' ',owner_last_name) AS turf_name FROM turfs"
		data['slots']=select(q)
		return render_template("admin_view_slots_amount.html",data=data)
	else:
		return redirect(url_for("public.login"))

@admin.route("/admin_turf_slots")
def admin_turf_slots():
	if not session.get("lid") is None:
		data={}
		tid=request.args['tid']
		q="SELECT *,CONCAT(owner_first_name,' ',owner_last_name) AS turf_name FROM slots inner join turfs using(turf_id) where turf_id='%s'"%(tid)
		data['slots']=select(q)
		return render_template("admin_turf_slots.html",data=data)
	else:
		return redirect(url_for("public.login"))



@admin.route("/admin_view_slot_details")
def admin_view_slot_details():
	if not session.get("lid") is None:
		data={}
		tid=request.args['tid']
		q="SELECT * FROM slots where turf_id='%s'"%(tid)
		data['ss']=select(q)
		return render_template("admin_view_slot_details.html",data=data)
	else:
		return redirect(url_for("public.login"))