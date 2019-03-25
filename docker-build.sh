VERSION=$1

APP_NAME=sas-auth-service
DOCKER_IMAGE_PREFIX=hub.mtop.vn
DOCKER_IMAGE=${DOCKER_IMAGE_PREFIX}/${APP_NAME}:$VERSION

branch_name=deploy-$VERSION
branch_is_exist=`git rev-parse --verify --quiet $branch_name`
if [ -n "$branch_is_exist" ]; then
	git branch -D $branch_name
fi
git checkout -b $branch_name $VERSION

mvn clean install
docker build --build-arg JAR_FILE=${APP_NAME}.jar -t $DOCKER_IMAGE .
docker push $DOCKER_IMAGE