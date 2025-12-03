import api from './api';

const AUDIT_API_URL = 'http://localhost:8084/api/bitacora';

export const getBitacora = async () => {
    const response = await api.get(AUDIT_API_URL);
    return response.data;
};