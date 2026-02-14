import React from 'react';
import { useAuth } from '../context/AuthContext';
import '../styles/Dashboard.css';

const ProfilePage = () => {
  const { user, logout } = useAuth();

  return (
    <div className="dashboard-container">
      <nav className="dashboard-nav">
        <div className="nav-brand">
          <h2>IntegSystem</h2>
        </div>
        <button className="logout-btn" onClick={logout}>
          Logout
        </button>
      </nav>

      <div className="dashboard-content">
        <div className="profile-card">
          <div className="profile-header">
            <div className="avatar">{user?.name?.charAt(0)?.toUpperCase()}</div>
            <div className="profile-info">
              <h1>{user?.name || `${user?.first_name || ''} ${user?.last_name || ''}`.trim() || 'User'}</h1>
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
              <span className="label">First Name:</span>
              <span className="value">{user?.first_name || user?.name}</span>
            </div>
            <div className="detail-item">
              <span className="label">Last Name:</span>
              <span className="value">{user?.last_name || ''}</span>
            </div>
            <div className="detail-item">
              <span className="label">User ID:</span>
              <span className="value">{user?.id}</span>
            </div>
            <div className="detail-item">
              <span className="label">Raw Profile:</span>
              <pre className="value" style={{ whiteSpace: 'pre-wrap' }}>{JSON.stringify(user, null, 2)}</pre>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
