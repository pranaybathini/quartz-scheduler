# Step 0: A. Stop existing container and remove it.  B. Remove the old image.
docker ps -q --filter "name=quartz-scheduler" | grep -q . && docker stop quartz-scheduler && docker rm -fv quartz-scheduler && docker rmi quartz-scheduler

# Step 1: Create a new jar
mvn clean install

# Step 2: Dockerize with Dockerfile
docker build -t quartz-scheduler .

# Step 3: Run the container
docker run --name quartz-scheduler --add-host=host.docker.internal:host-gateway --env SPRING_PROFILES_ACTIVE=local-postgres -p 9166:9166 -d quartz-scheduler