import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { loginService } from '../../services/authService';
import './Login.css';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            const data = await loginService(username, password);
            // data.token es el JWT que devuelve tu backend
            login(data.token, { username }); // Guardamos en el contexto
            // Redirigir al dashboard (cuando lo creemos)
            // navigate('/dashboard');
            alert("Login exitoso! Token guardado."); // Temporal para pruebas
        } catch (err) {
            setError('Credenciales inválidas. Por favor intenta de nuevo.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <div className="login-header">
                    {/* Placeholder para el icono de la cámara/restaurante */}
                    <span className="logo-placeholder">📷</span>
                    <h1>Sabor Gourmet</h1>
                    <p>Ingresa a tu cuenta</p>
                </div>

                {error && <div className="error-message">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username">Usuario</label>
                        <div className="input-with-icon">
                            <span className="input-icon-placeholder">👤</span>
                            <input
                                type="text"
                                id="username"
                                placeholder="Ingresa tu usuario"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        </div>
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Contraseña</label>
                        <div className="input-with-icon">
                            <span className="input-icon-placeholder">🔒</span>
                            <input
                                type="password"
                                id="password"
                                placeholder="Ingresa tu contraseña"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>
                    </div>

                    <button type="submit" className="btn-primary" disabled={loading}>
                        {loading ? 'Cargando...' : 'Iniciar Sesión'}
                    </button>
                </form>

                <div className="register-link">
                    <p>¿No tienes cuenta? <a href="#">Regístrate</a></p>
                </div>

                <div className="login-footer">
                    <p>Credenciales de prueba:</p>
                    <div className="footer-credentials">
                        Usuario: admin | Contraseña: password123
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;