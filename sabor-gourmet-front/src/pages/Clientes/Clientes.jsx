import React, { useEffect, useState } from 'react';
import { getClientes, createCliente, updateCliente, deleteCliente } from '../../services/clienteService';
import { useAuth } from '../../context/AuthContext';
import './Clientes.css';

const Clientes = () => {
    const [clientes, setClientes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const { user } = useAuth();

    // Estado del formulario
    const initialFormState = { id: null, nombres: '', apellidos: '', dni: '', email: '', telefono: '' };
    const [formData, setFormData] = useState(initialFormState);

    const fetchClientes = async () => {
        try {
            setLoading(true);
            const data = await getClientes();
            setClientes(data || []);
        } catch (error) {
            console.error("Error al cargar clientes", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchClientes();
    }, []);

    const handleOpenCreate = () => {
        setFormData(initialFormState);
        setIsEditing(false);
        setShowModal(true);
    };

    const handleOpenEdit = (cliente) => {
        setFormData(cliente);
        setIsEditing(true);
        setShowModal(true);
    };

    const handleDelete = async (id) => {
        if (window.confirm("¿Estás seguro de desactivar este cliente?")) {
            try {
                await deleteCliente(id);
                fetchClientes();
            } catch (error) {
                alert("Error al eliminar cliente");
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isEditing) {
                await updateCliente(formData.id, formData);
            } else {
                await createCliente(formData);
            }
            setShowModal(false);
            fetchClientes();
        } catch (error) {
            alert("Error al guardar cliente. Verifique que el DNI no esté duplicado.");
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    if (loading) return <p>Cargando clientes...</p>;

    return (
        <div>
            <div className="page-header">
                <h1>Directorio de Clientes</h1>
                {(user?.rol === 'ADMIN' || user?.rol === 'MOZO') && (
                    <button className="btn-primary" style={{ width: 'auto' }} onClick={handleOpenCreate}>
                        + Nuevo Cliente
                    </button>
                )}
            </div>

            <div className="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>DNI</th>
                        <th>Nombre Completo</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {clientes.map((cliente) => (
                        <tr key={cliente.id}>
                            <td>{cliente.dni}</td>
                            <td>{cliente.nombres} {cliente.apellidos}</td>
                            <td>{cliente.email || '-'}</td>
                            <td>{cliente.telefono || '-'}</td>
                            <td>
                  <span className={cliente.activo ? 'status-active' : 'status-inactive'}>
                    {cliente.activo ? 'ACTIVO' : 'INACTIVO'}
                  </span>
                            </td>
                            <td>
                                {(user?.rol === 'ADMIN' || user?.rol === 'MOZO') && (
                                    <button className="action-btn btn-edit" onClick={() => handleOpenEdit(cliente)} title="Editar">✏️</button>
                                )}
                                {user?.rol === 'ADMIN' && (
                                    <button className="action-btn btn-delete" onClick={() => handleDelete(cliente.id)} title="Eliminar">🗑️</button>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            {/* Modal Formulario */}
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>{isEditing ? 'Editar Cliente' : 'Nuevo Cliente'}</h2>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label>DNI</label>
                                <input name="dni" value={formData.dni} onChange={handleChange} required minLength="8" maxLength="8" />
                            </div>
                            <div className="form-group">
                                <label>Nombres</label>
                                <input name="nombres" value={formData.nombres} onChange={handleChange} required />
                            </div>
                            <div className="form-group">
                                <label>Apellidos</label>
                                <input name="apellidos" value={formData.apellidos} onChange={handleChange} required />
                            </div>
                            <div className="form-group">
                                <label>Email</label>
                                <input type="email" name="email" value={formData.email} onChange={handleChange} />
                            </div>
                            <div className="form-group">
                                <label>Teléfono</label>
                                <input name="telefono" value={formData.telefono} onChange={handleChange} />
                            </div>
                            <div className="modal-actions">
                                <button type="button" onClick={() => setShowModal(false)} style={{ background: '#ccc', border: 'none', padding: '10px', borderRadius: '8px', cursor: 'pointer' }}>Cancelar</button>
                                <button type="submit" className="btn-primary" style={{ marginTop: 0, width: 'auto' }}>Guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Clientes;