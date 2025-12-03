import React, { createContext, useState, useEffect, useContext } from 'react';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const login = (newToken, userData) => {
        localStorage.setItem('jwt_token', newToken);
        localStorage.setItem('user_data', JSON.stringify(userData)); // Guardamos datos del usuario
        setToken(newToken);
        setUser(userData);
        setIsAuthenticated(true);
    };

// Al cargar, recuperar usuario del storage
useEffect(() => {
    const storedUser = localStorage.getItem('user_data');
    if (storedUser) {
        setUser(JSON.parse(storedUser));
    }
}, []);

// Al cargar, recuperar usuario del storage
useEffect(() => {
    const storedUser = localStorage.getItem('user_data');
    if (storedUser) {
        setUser(JSON.parse(storedUser));
    }
}, []);
    const login = (newToken, userData) => {
        localStorage.setItem('jwt_token', newToken);
        setToken(newToken);
        setUser(userData);
        setIsAuthenticated(true);
    };

    const logout = () => {
        localStorage.removeItem('jwt_token');
        setToken(null);
        setUser(null);
        setIsAuthenticated(false);
    };

    // Opcional: Verificar si el token sigue siendo válido al cargar la app
    useEffect(() => {
        // Aquí podrías llamar a /api/auth/validate si lo implementaste
    }, []);

    return (
        <AuthContext.Provider value={{ user, token, isAuthenticated, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};