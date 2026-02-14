import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import '../styles/Dashboard.css';

const DashboardPage = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="dashboard-container">
      <nav className="dashboard-nav">
        <div className="nav-brand">
          <h2 className="nav-profile-title">
            <svg className="nav-profile-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" aria-hidden="true">
              <circle cx="12" cy="8" r="3"></circle>
              <path d="M4 20v-1a7 7 0 0 1 14 0v1"></path>
            </svg>
            Profile
          </h2>
        </div>
        <div style={{ display: 'flex', gap: 8 }}>
          <button className="logout-btn" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </nav>

      <div className="dashboard-content">
        <div className="profile-card">
          <div className="profile-header">
            <div className="avatar">{user?.name?.charAt(0).toUpperCase()}</div>
            <div className="profile-info">
              <h1 className="profile-title">
                <svg className="profile-title-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" aria-hidden="true">
                  <circle cx="12" cy="8" r="3"></circle>
                  <path d="M4 20v-1a7 7 0 0 1 14 0v1"></path>
                </svg>
                {user?.name || 'User'}
              </h1>
              <p>{user?.email}</p>
            </div>
          </div>

          <div className="profile-details">
            <h2>Profile Information</h2>
            <div className="detail-item">
              <span className="label">Email:</span>
              <span className="value">{user?.email}</span>
            </div>
            <div className="detail-item">
              <span className="label">Name:</span>
              <span className="value">{user?.name}</span>
            </div>
            <div className="detail-item">
              <span className="label">User ID:</span>
              <span className="value">{user?.id}</span>
            </div>
          </div>

          
        </div>

        
      </div>
    </div>
  );
};

export default DashboardPage;
