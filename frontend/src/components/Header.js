import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Building, Users, Armchair, Info, Activity } from 'lucide-react';
import NotificationCenter from './NotificationCenter';

const Header = () => {
  const location = useLocation();

  const navItems = [
    { path: '/', label: 'Interactive Dashboard', icon: Activity },
    { path: '/business-rules', label: 'Business Rules', icon: Info },
    { path: '/employees', label: 'Employees', icon: Users },
    { path: '/seats', label: 'Seats', icon: Armchair },
  ];


  return (
    <header className="glassmorphism-bg border-b border-white/20 sticky top-0 z-50">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center space-x-4">
            <Building className="h-8 w-8 text-primary" />
            <h1 className="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
              Workspace Allocation System
            </h1>
          </div>

          
          <div className="flex items-center space-x-4">
            {/* Navigation */}
            <nav className="flex space-x-1">
              {navItems.map(({ path, label, icon: Icon }) => (
                <Link
                  key={path}
                  to={path}
                  className={`flex items-center space-x-2 px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                    location.pathname === path
                      ? 'bg-primary text-primary-foreground shadow-lg scale-105'
                      : 'text-foreground/80 hover:text-foreground hover:bg-white/10 hover:scale-105'
                  }`}
                >
                  <Icon className="h-4 w-4" />
                  <span className="hidden lg:inline">{label}</span>
                </Link>
              ))}
            </nav>

            {/* Notifications */}
            <NotificationCenter />
          </div>
        </div>

      </div>
    </header>
  );
};

export default Header;