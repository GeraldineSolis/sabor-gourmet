import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, Outlet } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login/Login';
import DashboardLayout from './layouts/DashboardLayout';
import Mesas from './pages/Mesas/Mesas';
// 👇 Importamos las nuevas vistas
import Clientes from './pages/Clientes/Clientes';
import Audit from './pages/Audit/Audit';

const ProtectedRoute = ({ requiredRole }) => {
    const { isAuthenticated, user } = useAuth();

    if (!isAuthenticated) return <Navigate to="/login" replace />;

    // Si se requiere un rol específico (ej: ADMIN) y el usuario no lo tiene
    if (requiredRole && user?.rol !== requiredRole) {
        return <div style={{padding: 20}}>Acceso denegado: No tienes permisos suficientes.</div>;
    }

    return <Outlet />;
};

function App() {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/login" element={<Login />} />

                    {/* Rutas para usuarios logueados */}
                    <Route element={<ProtectedRoute />}>
                        <Route path="/dashboard" element={<DashboardLayout />}>
                            <Route index element={<Navigate to="mesas" replace />} />
                            <Route path="mesas" element={<Mesas />} />
                            <Route path="clientes" element={<Clientes />} />
                        </Route>
                    </Route>

                    {/* Ruta exclusiva para ADMIN (Auditoría) */}
                    <Route element={<ProtectedRoute requiredRole="ADMIN" />}>
                        <Route path="/dashboard" element={<DashboardLayout />}>
                            <Route path="audit" element={<Audit />} />
                        </Route>
                    </Route>

                    <Route path="*" element={<Navigate to="/login" replace />} />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;