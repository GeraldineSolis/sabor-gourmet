import React, { useEffect, useState } from 'react';
import { getBitacora } from '../../services/auditService';
import './Audit.css';

const Audit = () => {
    const [logs, setLogs] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchLogs = async () => {
            try {
                const data = await getBitacora();
                // Ordenar por fecha descendente (más reciente primero)
                const sortedData = data.sort((a, b) => new Date(b.fechaHora) - new Date(a.fechaHora));
                setLogs(sortedData);
            } catch (error) {
                console.error("Error cargando bitácora", error);
            } finally {
                setLoading(false);
            }
        };
        fetchLogs();
    }, []);

    const getActionClass = (accion) => {
        if (accion.includes('CREATE')) return 'bg-create';
        if (accion.includes('UPDATE')) return 'bg-update';
        if (accion.includes('DELETE')) return 'bg-delete';
        return 'bg-default';
    };

    if (loading) return <p>Cargando registros de auditoría...</p>;

    return (
        <div>
            <div className="page-header">
                <h1>Bitácora de Auditoría</h1>
            </div>

            <div className="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Fecha/Hora</th>
                        <th>Usuario</th>
                        <th>Acción</th>
                        <th>Entidad</th>
                        <th>Detalle</th>
                    </tr>
                    </thead>
                    <tbody className="audit-log">
                    {logs.map((log) => (
                        <tr key={log.id}>
                            <td>{new Date(log.fechaHora).toLocaleString()}</td>
                            <td><strong>{log.username}</strong></td>
                            <td>
                  <span className={`badge-action ${getActionClass(log.accion)}`}>
                    {log.accion}
                  </span>
                            </td>
                            <td>{log.entidad} (ID: {log.entidadId})</td>
                            <td>{log.detalle}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Audit;