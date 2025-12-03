# ----------------------------
# Etapa 1: Construcción (Build)
# ----------------------------
FROM node:18-alpine as build-stage

WORKDIR /app

# Copiamos archivos de dependencias
COPY package*.json ./

# Instalamos dependencias
RUN npm install

# Copiamos el código fuente
COPY .. .

# Generamos los archivos estáticos para producción (carpeta /dist)
RUN npm run build

# ----------------------------
# Etapa 2: Servidor de Producción (Nginx)
# ----------------------------
FROM nginx:alpine as production-stage

# Copiamos los archivos generados en la etapa anterior (dist) a la carpeta pública de Nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copiamos nuestra configuración personalizada de Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponemos el puerto 80
EXPOSE 80

# Iniciamos Nginx
CMD ["nginx", "-g", "daemon off;"]