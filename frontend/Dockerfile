FROM node:20-alpine AS build
ARG VITE_API_URL=http://localhost:8080/api
ARG VITE_FACE_MODELS_URL=https://raw.githubusercontent.com/justadudewhohacks/face-api.js/master/weights
WORKDIR /app
ENV VITE_API_URL=$VITE_API_URL
ENV VITE_FACE_MODELS_URL=$VITE_FACE_MODELS_URL
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:1.27-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
