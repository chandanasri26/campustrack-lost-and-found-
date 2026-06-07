# Production Deployment Guide

## 1. Environment variables

Copy `.env.example` to `.env` and fill in your production values.

Required backend values:
- `MONGODB_URI`
- `JWT_SECRET` (64+ chars, letters and numbers)
- `ALLOWED_ORIGINS`

Required frontend values:
- `VITE_API_URL`
- `VITE_FACE_MODELS_URL`

## 2. Local verification

```bash
cd backend && mvn -DskipTests package
cd ../frontend && npm install && npm run build
```

## 3. Docker

```bash
docker compose up --build
```

## 4. Hosting targets

- Frontend: Vercel or Netlify
- Backend: Render / Railway / Fly.io
- Database: MongoDB Atlas

## 5. Security checklist

- Use HTTPS everywhere
- Keep `JWT_SECRET` in hosting secrets
- Restrict `ALLOWED_ORIGINS` to production frontend domains
- Do not log passwords, JWTs, or raw user tokens
- Trust `X-Forwarded-*` headers on the reverse proxy
