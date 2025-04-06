# Dockerfile
# 1st stage: build the React app
FROM node:20 AS build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# inject environment variables at build time
ARG REACT_APP_API_BASE_URL
ARG REACT_APP_LLM_BASE_URL
ENV REACT_APP_API_BASE_URL=$REACT_APP_API_BASE_URL
ENV REACT_APP_LLM_BASE_URL=$REACT_APP_LLM_BASE_URL

RUN npm run build

# 2nd stage: serve static files
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# optional: can do nginx config constumization here
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
