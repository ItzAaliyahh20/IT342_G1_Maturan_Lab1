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
                  <h2>IntegSystem</h2>
                </div>
                <button className="logout-btn" onClick={handleLogout}>
                  Logout
                </button>
              </nav>

              <div className="dashboard-content">
                <div className="profile-card">
                  <div className="profile-header">
                    <div className="avatar">{user?.name?.charAt(0).toUpperCase()}</div>
                    <div className="profile-info">
                      <h1>{user?.name || 'User'}</h1>
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

                  <div className="profile-actions">
                    <button className="btn-secondary">Edit Profile</button>
                    <button className="btn-secondary">Change Password</button>
                  </div>
                </div>

                <div className="dashboard-grid">
                  <div className="dashboard-card">
                    <h3>Quick Stats</h3>
                    <div className="stat">
                      <span className="stat-label">Account Status</span>
                      <span className="stat-value">Active</span>
                    </div>
                  </div>

                  <div className="dashboard-card">
                    <h3>Recent Activity</h3>
                    <p>No recent activity</p>
                  </div>
                </div>
              </div>
            </div>
          );
        };

        export default DashboardPage;
