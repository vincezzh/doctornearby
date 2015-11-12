# Deploy war file to application server
sftp 56206fd87628e1d926000135@doctornearby-akhaltech.rhcloud.com
cd app-root/dependencies/jbossews/webapps
put <file-name>.war


------ DB Index ------
db.doctor.createIndex( { "profile.surname": 1, "profile.givenName": 1 } )
db.doctor.createIndex( { "province": 1 } )
db.doctor.createIndex( { "location.addressSummary": 1 } )
db.doctor.createIndex( { "specialtyList.name": 1 } )
db.doctor.createIndex( { "profile.languageList": 1 } )
db.doctor.createIndex( { "profile.surname": 1 } )
db.doctor.createIndex( { "profile.givenName": 1 } )
db.doctor.createIndex( { "profile.gender": 1 } )
db.doctor.createIndex( { "registration.registrationStatus": 1 } )
db.doctor.createIndex( { "privilegeList.hospitalDetail": 1 } )
db.user_medicine.createIndex( { "userId": 1 } )
db.user_bookmark.createIndex( { "userId": 1 } )