import axios from 'axios';

// URL base del Auth Service
export const AUTH_API_URL = 'http://localhost:8081/api/auth';

// Puedes crear instancias específicas si lo prefieres, por ahora usaremos axios directo
const api = axios.create({
    headers: {
        'Content-Type': 'application/json',
    },
});

// Interceptor para añadir el token automáticamente a futuras peticiones (para los otros servicios)
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('jwt_token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default api;