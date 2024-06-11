from flask import *
from database import *
# import demjson

api=Blueprint("api",__name__)




@api.route('/login',methods=['get','post'])
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	
	q="select * from login where username='%s' and password='%s'" %(username,password)
	res=select(q)

	if res:
		data['status']="success"
		data['method']="login"
		data['data']=res
	else:
		data['status']="failed"
		data['method']="login"
	return str(data)

@api.route('/register',methods=['get','post'])
def register():
	data={}
	first_name=request.args['fname']
	last_name=request.args['lname']
	house_name=request.args['hname']
	place=request.args['place']
	pincode=request.args['pin']
	phone=request.args['phone']
	email=request.args['email']
	username=request.args['uname']
	password=request.args['pass']
	q="insert into login values(NULL,'%s','%s','user')"%(username,password)
	id=insert(q)
	q="insert into users values (NULL,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,first_name,last_name,house_name,place,pincode,phone,email)
	iid=insert(q)
	if iid:
		data['status']="success"
		data['method']="register"
		
	else:
		data['status']="failed"
		data['method']="register"
	return str(data)




@api.route('/usersendfeedback',methods=['get','post'])
def usersendfeedback():
	data={}
	login_id=request.args['loginid']
	q="select * from users where login_id='%s'"%(login_id)
	res=select(q)
	book_id=request.args['book_id']
	q="select * from bookings inner join slots using(slot_id) inner join turfs using(turf_id) where book_id='%s'"%(book_id)
	res1=select(q)
	title=request.args['title']
	description=request.args['feed_des']
	q="insert into feedbacks values(NULL,'%s','%s','%s','%s',now())"%(res[0]['user_id'],res1[0]['turf_id'],title,description)
	insert(q)
	data['status']="success"
	data['method']="usersendfeedback"
	return str(data)
# @api.route('/userviewfeedback/',methods=['get','post'])
# def userviewfeedback():
# 	data={}
# 	login_id=request.args['loginid']
# 	q="select * from users where login_id='%s'"%(login_id)
# 	res=select(q)
# 	q="select * from feedbacks inner join users using(user_id) inner join turf using(turf_id)"
# 	res=select(q)
# 	if res:
# 		data['data']=res
# 		data['method']="userviewfeedback"
# 		data['status']="success"
# 	else:
# 		data['method']="userviewfeedback"
# 		data['status']="failed"

# 	return str(data)



@api.route('/ViewTurf',methods=['get','post'])
def ViewTurf():
	data={}
	latitude=request.args['latitude']
	logitude=request.args['logitude']
	q="select *,concat(turfs.owner_first_name,'',turfs.owner_last_name) as owner_name from turfs "
	res=select(q)
	if res:
		data['data']=res
		
		data['status']="success"
	else:
		data['status']="failed"
	data['method']="ViewTurf"
		
	return str(data)

@api.route('/ViewSlots',methods=['get','post'])
def ViewSlots():
	data={}
	turf_id=request.args['turf_ids']
	q="select * from slots where turf_id='%s'"%(turf_id)
	res=select(q)
	if res:
		data['data']=res
		data['status']="success"
	else:
		data['status']="failed"
	data['method']='ViewSlots'
	return str(data)


@api.route('/ViewFacilities',methods=['get','post'])
def ViewFacilities():
	data={}
	turf_id=request.args['turf_ids']
	q="select * from facilities where turf_id='%s'"%(turf_id)
	res=select(q)
	if res:
		data['data']=res
		data['status']="success"
	else:
		data['status']="failed"
	data['method']='ViewFacilities'
	return str(data)


@api.route('/BookSlot',methods=['get','post'])
def BookSlot():
	data={}
	slot_id=request.args['slot_ids']
	loginid=request.args['loginid']
	print(loginid,'////////////////////////////////////////')
	q="select * from users where login_id='%s'"%(loginid)
	res=select(q)
	q="select * from bookings where slot_id='%s'"%(slot_id)
	res1=select(q)
	if res1:
		data['status']="already_booked"
	else:
		q="insert into  bookings values(NULL,'%s','%s',now(),'pending')"%(res[0]['user_id'],slot_id)
		id=insert(q)
		if id:
			data['status']="success"
		else:
			data['status']="failed"
	data['method']='BookSlot'
	return str(data)
@api.route('/Bookings',methods=['get','post'])
def Bookings():
	data={}
	login_id=request.args['login_id']
	q="select * from users where login_id='%s'"%(login_id)

	res=select(q)
	q="select *,concat(turfs.owner_first_name,'',turfs.owner_last_name) as owner_name from bookings inner join slots using(slot_id) inner join turfs using(turf_id) where user_id='%s'"%(res[0]['user_id'])
	
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']='Bookings'
	return str(data)


@api.route('/User_Send_feedback',methods=['get','post'])
def User_Send_feedback():
	data={}
	login_id=request.args['loginid']
	book_ids=request.args['book_ids']

	q="insert into users(NULL,'%s','%s','%s','%s','%s','%s','%s','%s')"%(id,first_name,last_name,house_name,place,pincode,phone,email)
	iid=insert(q)
	if iid:
		data['status']="success"
		data['method']="register"
		
	else:
		data['status']="failed"
		data['method']="register"
	return str(data)


@api.route('/userviewfeedback',methods=['get','post'])
def userviewfeedback():
	data={}
	loginid=request.args['loginid']
	q="select * from users where login_id='%s'"%(loginid)
	res1=select(q)
	q="select *,concat(turfs.owner_first_name,'',turfs.owner_last_name) as turf from feedbacks inner join turfs using(turf_id) where user_id='%s'"%(res1[0]['user_id'])
	res=select(q)
	if res:
		data['status']="success"
		data['method']="userviewfeedback"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="userviewfeedback"
	return str(data)



@api.route('/usersendcomplaint',methods=['get','post'])
def usersendcomplaint():
	data={}
	loginid=request.args['loginid']
	complaint=request.args['complaint']
	q="select * from users where login_id='%s'"%(loginid)
	res1=select(q)
	q="insert into complaints values(NULL,'%s','%s','pending',curdate())"%(res1[0]['user_id'],complaint)
	id=insert(q)
	if id:
		data['status']="success"
		data['method']="usersendcomplaint"
		data['data']=id
		
	else:
		data['status']="failed"
		data['method']="usersendcomplaint"
	return str(data)



@api.route('/userviewcomplaints',methods=['get','post'])
def userviewcomplaints():
	data={}
	loginid=request.args['loginid']
	q="select * from users where login_id='%s'"%(loginid)
	res1=select(q)
	q="select * from complaints where user_id='%s'"%(res1[0]['user_id'])
	res=select(q)
	if res:
		data['status']="success"
		data['method']="userviewcomplaint"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="userviewcomplaint"
	return str(data)

@api.route('/turfgallery',methods=['get','post'])
def turfgallery():
	data={}
	q="select *,concat(turfs.owner_first_name,'',turfs.owner_last_name) as name from images inner join turfs using(turf_id)"
	res=select(q)
	if res:
		data['status']="success"
		data['method']="turfgallery"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="turfgallery"
	return str(data)



@api.route('/viewusers',methods=['get','post'])
def viewusers():
	data={}
	login_id=request.args['loginid']
	q="select *,concat(users.first_name,'',users.last_name) as name from users where login_id!='%s'"%(login_id)
	res=select(q)
	if res:
		data['status']="success"
		data['method']="viewusers"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="viewusers"
	return str(data)


@api.route('/ScheduleMatch',methods=['get','post'])
def ScheduleMatch():
	data={}
	book_id=request.args['book_id']
	opp_user_id=request.args['opp_user_id']
	login_id=request.args['loginid']
	q="select * from users where login_id='%s'"%(login_id)
	res1=select(q)
	q="insert into matches values(NULL,'%s','%s','pending')"%(book_id,opp_user_id)
	id=insert(q)
	if id:
		data['status']="success"
		data['method']="ScheduleMatches"
		data['data']=id
		
	else:
		data['status']="failed"
		data['method']="ScheduleMatches"
	return str(data)


@api.route('/MyMatches',methods=['get','post'])
def MyMatches():
	data={}
	login_id=request.args['login_id']
	q="select * from users where login_id='%s'"%(login_id)
	res1=select(q)
	print(res1)
	q="select *,concat(users.first_name,'',users.last_name) as user_name,concat(turfs.owner_first_name,'',turfs.owner_last_name) as owner_name from bookings inner join slots using(slot_id) inner join turfs using(turf_id) inner join matches using(book_id) inner join users on users.user_id=matches.opp_user_id where bookings.user_id='%s'"%(res1[0]['user_id'])
	res=select(q)
	if res:
		data['status']="success"
		data['method']="MyMatches"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="MyMatches"
	return str(data)





@api.route('/MatchRequests',methods=['get','post'])
def MatchRequests():
	data={}
	login_id=request.args['login_id']
	q="select * from users where login_id='%s'"%(login_id)
	res1=select(q)
	q="select *,concat(users.first_name,'',users.last_name) as user_name,concat(turfs.owner_first_name,'',turfs.owner_last_name) as owner_name from bookings inner join slots using(slot_id) inner join turfs using(turf_id) inner join matches using(book_id) inner join users on users.user_id=bookings.user_id where opp_user_id='%s'"%(res1[0]['user_id'])
	res=select(q)
	if res:
		data['status']="success"
		data['method']="MatchRequests"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="MatchRequests"
	return str(data)


@api.route('/MatchAccept',methods=['get','post'])
def MatchAccept():
	data={}
	match_id=request.args['match_id']
	q="update matches set status='accepted' where match_id='%s'"%(match_id)
	res=update(q)
	if res:
		data['status']="success"
		data['method']="MatchAccept"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="MatchAccept"
	return str(data)



@api.route('/MatchReject',methods=['get','post'])
def MatchReject():
	data={}
	match_id=request.args['match_id']
	q="update matches set status='rejected' where match_id='%s'"%(match_id)
	res=update(q)
	if res:
		data['status']="success"
		data['method']="MatchReject"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="MatchReject"
	return str(data)

@api.route('/Review',methods=['get','post'])
def Review():
	data={}
	login_id=request.args['login_id']
	turf_id=request.args['turf_id']
	rate=request.args['rating']
	review=request.args['review']

	q="select * from users where login_id='%s'"%(login_id)
	res=select(q)
	q="select * from ratings where user_id='%s' and turf_id='%s'"%(res[0]['user_id'],turf_id)
	res1=select(q)
	if res1:
		q="update ratings set rate='%s',review='%s' where rate_id='%s'"%(rate,review,res1[0]['rate_id'])
		id=update(q)
	else:

		q="insert into ratings values(NULL,'%s','%s','%s','%s',now())"%(res[0]['user_id'],turf_id,rate,review)
		id=insert(q)
	if id:
		data['status']="success"
		data['method']="Review"
		data['data']=id
		
	else:
		data['status']="failed"
		data['method']="Review"
	return str(data)



@api.route('/ViewReview',methods=['get','post'])
def ViewReview():
	data={}
	login_id=request.args['login_id']
	turf_id=request.args['turf_id']
	q="select * from users where login_id='%s'"%(login_id)
	res=select(q)
	q="select * from ratings inner join turfs using(turf_id) where turf_id='%s' and user_id='%s' "%(turf_id,res[0]['user_id'])
	res=select(q)
	if res:
		data['status']="success"
		data['method']="ViewReview"
		data['data']=res[0]['rate']
		data['data1']=res[0]['review']
		
	else:
		data['status']="failed"
		data['method']="ViewReview"
	return str(data)

@api.route('/payments',methods=['get','post'])
def payments():
	data={}
	book_id=request.args['book_id']
	amount=request.args['amount']
	q="insert into payments values(NULL,'%s','slot','online',now(),'%s')"%(book_id,amount)
	insert(q)
	q="update bookings set status='paid' where book_id='%s'"%(book_id)
	id=update(q)
	if id:
		data['status']="success"
		data['method']="payments"
		data['data']=id
	
		
	else:
		data['status']="failed"
		data['method']="payments"
	return str(data)


@api.route('/chat')
def chat():
	data={}
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']
	sender_type=request.args['sender_type']
	receiver_type=request.args['receiver_type']
	details=request.args['details']
	q="insert into chat values(null,'%s','%s','%s','%s','%s',now())" %(sender_id,sender_type,receiver_id,receiver_type,details)
	insert(q)
	data['status']="success"
	data['method']="chat"
	return str(data)


@api.route('/chatdetail')
def chatdetail():
	data={}
	sender_id=request.args['sender_id']
	receiver_id=request.args['receiver_id']
	q="SELECT * FROM chat WHERE (sender_id='%s' AND receiver_id='%s') or (sender_id='%s' AND receiver_id='%s')" %(sender_id,receiver_id,receiver_id,sender_id)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="chatdetail"
	return str(data)


@api.route('/userviewcoaches')
def userviewcoaches():
	data={}
	tid=request.args['tid']
	q="select * from coaches where turf_id='%s'"%(tid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userviewcoaches"
	return str(data)


@api.route('/usersendrequesttocoach')
def usersendrequesttocoach():
	data={}
	lid=request.args['lid']
	cid=request.args['cid']
	q="insert into `request` values(null,(select user_id from users where login_id='%s'),'%s','pending')"%(lid,cid)
	insert(q)
	data['status']="success"
	data['method']="usersendrequesttocoach"
	return str(data)


@api.route('/userview_videos')
def userview_videos():
	data={}
	tid=request.args['tid']
	q="select * from videos where training_id='%s'"%(tid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']="userview_videos"
	return str(data)


@api.route('/userviewtrainings')
def userviewtrainings():
	data={}
	cid=request.args['cid']
	q="select * from `training` where coach_id='%s'"%(cid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)



@api.route('/userviewrequesttocoach')
def userviewrequesttocoach():
	data={}
	cid=request.args['cid']
	lid=request.args['lid']
	q="select * from `request` inner join coaches using(coach_id) where coach_id='%s' and user_id=(select user_id from users where login_id='%s')"%(cid,lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)



@api.route('/usermakepayment',methods=['get','post'])
def usermakepayment():
	data={}
	rid=request.args['rid']
	amount=request.args['amount']
	q="insert into payments values(NULL,'%s','coach','online',now(),'%s')"%(rid,amount)
	insert(q)
	q="update `request` set status='paid' where request_id='%s'"%(rid)
	update(q)
	data['status']="success"
	return str(data)