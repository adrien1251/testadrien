FROM nginx

RUN mkdir -p /var/www/html/front
COPY dist/compare-tout /usr/share/nginx/html/

CMD sed -i "s/listen 8080/listen ${PORT:-8080}/" /etc/nginx/conf.d/default.conf && exec nginx -g "daemon off;"

