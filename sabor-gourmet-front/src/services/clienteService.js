import api from './api';

const CLIENTE_API_URL = 'http://localhost:8082/api/clientes';

export const getClientes = async () => {
    const response = await api.get(CLIENTE_API_URL);
    return response.data.content; // Spring devuelve paginación en .content
};

export const createCliente = async (clienteData) => {
    const response = await api.post(CLIENTE_API_URL, clienteData);
    return response.data;
};

export const updateCliente = async (id, clienteData) => {
    const response = await api.put(`${CLIENTE_API_URL}/${id}`, clienteData);
    return response.data;
};

export const deleteCliente = async (id) => {
    // Esto hace un Soft Delete en el backend
    await api.delete(`${CLIENTE_API_URL}/${id}`);
};