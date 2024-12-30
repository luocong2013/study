from pymongo import MongoClient

MONGODB_URI = 'mongodb://tieba:tieba@192.168.0.104:27017/tieba'
# MONGODB_URI = 'mongodb://192.168.0.104:27017'
MONGODB_DB_NAME = 'tieba'
MONGODB_COLL_NAME = 'image'

db_client = MongoClient(MONGODB_URI)
db = db_client.get_database(MONGODB_DB_NAME)
# db.authenticate('tieba', 'tieba')
db_collection = db.get_collection(MONGODB_COLL_NAME)

images = db_collection.find_one()
image_name = images['image_name']
image_bin = images['image_bin']
print(image_name)
