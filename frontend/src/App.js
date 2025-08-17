import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import Header from './components/Header';
import UnifiedDashboard from './components/UnifiedDashboard';
import EmployeeManagement from './components/EmployeeManagement';
import SeatManagement from './components/SeatManagement';
import BusinessRulesDashboard from './components/BusinessRulesDashboard';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-background text-foreground">
        <Header />
        <main>
          <Routes>
            <Route path="/" element={<UnifiedDashboard />} />
            <Route path="/employees" element={<EmployeeManagement />} />
            <Route path="/seats" element={<SeatManagement />} />
            <Route path="/business-rules" element={<BusinessRulesDashboard />} />
          </Routes>
        </main>
        <Toaster 
          position="top-right"
          toastOptions={{
            duration: 4000,
            style: {
              background: 'hsl(var(--card))',
              color: 'hsl(var(--card-foreground))',
              border: '1px solid hsl(var(--border))',
            },
          }}
        />
      </div>
    </Router>
  );
}

export default App;