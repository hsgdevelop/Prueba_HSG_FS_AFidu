# ============================================================
# Stage 1 — Build
# Usa Node 20 LTS para compilar la aplicación Angular 19
# ============================================================
FROM node:20-alpine AS build

WORKDIR /app

# Copia los manifiestos de dependencias primero (cache de capas)
COPY package*.json ./
RUN npm ci --prefer-offline

# Copia el resto del proyecto Angular
COPY . .

# Compila en modo producción
# Angular 19 produce el output en dist/<nombre-proyecto>/browser
RUN npm run build -- --configuration production

# ============================================================
# Stage 2 — Serve
# nginx:alpine es la imagen más liviana para servir archivos estáticos
# ============================================================
FROM nginx:alpine

# Elimina la configuración por defecto de nginx
RUN rm /etc/nginx/conf.d/default.conf

# Copia la configuración personalizada con el proxy /api
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia los archivos compilados de Angular.
# Angular 19 con SSR deshabilitado genera en dist/<app>/browser,
# sin SSR puede ser dist/<app>. El glob cubre ambos casos.
COPY --from=build /app/dist/**/browser /usr/share/nginx/html/
# Fallback si el build no tiene subcarpeta browser
COPY --from=build /app/dist /usr/share/nginx/html/

# Puerto expuesto por nginx
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
