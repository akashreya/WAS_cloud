import React, { useState, useEffect } from 'react';
import { 
  Activity, 
  Users, 
  Armchair, 
  Crown,
  TrendingUp,
  TrendingDown,
  Minus,
  RefreshCw,
  AlertTriangle,
  CheckCircle,
  Clock
} from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Button } from './ui/button';
import { Progress } from './ui/progress';
import { Separator } from './ui/separator';
import { seatApi, employeeApi } from '../services/api';
import toast from 'react-hot-toast';

const SystemStatusPanel = ({ className }) => {
  const [stats, setStats] = useState({
    totalSeats: 0,
    occupiedSeats: 0,
    availableSeats: 0,
    managerSeats: 0,
    totalEmployees: 0,
    assignedEmployees: 0,
    unassignedEmployees: 0,
    queueSize: 0,
    seatUtilization: 0,
    managerSeatUsage: 0,
    trends: {
      seatsChange: 0,
      employeesChange: 0,
      utilizationChange: 0
    }
  });

  const [systemHealth, setSystemHealth] = useState({
    status: 'healthy',
    uptime: '99.9%',
    lastIncident: 'None',
    responseTime: '< 100ms'
  });

  const [loading, setLoading] = useState(true);
  const [lastUpdated, setLastUpdated] = useState(new Date());
  const [autoRefresh, setAutoRefresh] = useState(true);

  useEffect(() => {
    fetchSystemStatus();
    
    let interval;
    if (autoRefresh) {
      interval = setInterval(fetchSystemStatus, 30000); // Update every 30 seconds
    }
    
    return () => {
      if (interval) clearInterval(interval);
    };
  }, [autoRefresh]);

  const fetchSystemStatus = async () => {
    try {
      const [
        allSeatsRes,
        occupiedSeatsRes,
        availableSeatsRes,
        managerSeatsRes,
        allEmployeesRes,
        assignedEmployeesRes,
        unassignedEmployeesRes
      ] = await Promise.all([
        seatApi.getAll(),
        seatApi.getOccupied(),
        seatApi.getAvailable(),
        seatApi.getManagerSeats(),
        employeeApi.getAll(),
        employeeApi.getAssigned(),
        employeeApi.getUnassigned()
      ]);

      const newStats = {
        totalSeats: allSeatsRes.data.data.length,
        occupiedSeats: occupiedSeatsRes.data.data.length,
        availableSeats: availableSeatsRes.data.data.length,
        managerSeats: managerSeatsRes.data.data.length,
        totalEmployees: allEmployeesRes.data.data.length,
        assignedEmployees: assignedEmployeesRes.data.data.length,
        unassignedEmployees: unassignedEmployeesRes.data.data.length,
        queueSize: Math.min(unassignedEmployeesRes.data.data.length, 10)
      };

      // Calculate utilization percentages
      newStats.seatUtilization = newStats.totalSeats > 0 
        ? Math.round((newStats.occupiedSeats / newStats.totalSeats) * 100) 
        : 0;
      
      newStats.managerSeatUsage = Math.round((newStats.managerSeats / 5) * 100);

      // Calculate trends (simplified - in real app, you'd store historical data)
      newStats.trends = {
        seatsChange: Math.floor(Math.random() * 5) - 2, // Mock trend data
        employeesChange: Math.floor(Math.random() * 3) - 1,
        utilizationChange: Math.floor(Math.random() * 10) - 5
      };

      setStats(newStats);
      setLastUpdated(new Date());
      
      // Update system health based on current metrics
      updateSystemHealth(newStats);
      
    } catch (error) {
      toast.error('Failed to update system status');
      setSystemHealth(prev => ({ ...prev, status: 'error' }));
    } finally {
      setLoading(false);
    }
  };

  const updateSystemHealth = (currentStats) => {
    let status = 'healthy';
    let issues = [];

    // Check for potential issues
    if (currentStats.seatUtilization > 90) {
      status = 'warning';
      issues.push('High seat utilization');
    }
    
    if (currentStats.queueSize >= 10) {
      status = 'warning';
      issues.push('Queue at maximum capacity');
    }

    if (currentStats.managerSeats >= 5 && currentStats.managerSeatUsage === 100) {
      status = 'warning';
      issues.push('All manager seats allocated');
    }

    setSystemHealth(prev => ({
      ...prev,
      status,
      issues
    }));
  };

  const getTrendIcon = (value) => {
    if (value > 0) return <TrendingUp className="h-3 w-3 text-green-600" />;
    if (value < 0) return <TrendingDown className="h-3 w-3 text-red-600" />;
    return <Minus className="h-3 w-3 text-gray-400" />;
  };

  const getTrendColor = (value) => {
    if (value > 0) return 'text-green-600';
    if (value < 0) return 'text-red-600';
    return 'text-gray-400';
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'healthy': return 'text-green-600';
      case 'warning': return 'text-yellow-600';
      case 'error': return 'text-red-600';
      default: return 'text-gray-600';
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case 'healthy': return <CheckCircle className="h-4 w-4 text-green-600" />;
      case 'warning': return <AlertTriangle className="h-4 w-4 text-yellow-600" />;
      case 'error': return <AlertTriangle className="h-4 w-4 text-red-600" />;
      default: return <Activity className="h-4 w-4 text-gray-600" />;
    }
  };

  const formatUptime = () => {
    const now = new Date();
    const uptimeHours = Math.floor((now - new Date(now.getFullYear(), now.getMonth(), now.getDate())) / (1000 * 60 * 60));
    return `${uptimeHours}h ${now.getMinutes()}m`;
  };

  if (loading) {
    return (
      <Card className={`glass-card ${className}`}>
        <CardContent className="flex items-center justify-center py-8">
          <div className="loading-spinner h-8 w-8"></div>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card className={`glass-card ${className}`}>
      <CardHeader>
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2">
            <Activity className="h-5 w-5" />
            <CardTitle>System Status</CardTitle>
          </div>
          <div className="flex items-center space-x-2">
            <Button
              variant="ghost"
              size="sm"
              onClick={fetchSystemStatus}
              disabled={loading}
            >
              <RefreshCw className={`h-4 w-4 ${loading ? 'animate-spin' : ''}`} />
            </Button>
            <Button
              variant="ghost"
              size="sm"
              onClick={() => setAutoRefresh(!autoRefresh)}
            >
              <Badge variant={autoRefresh ? "success" : "secondary"} className="text-xs">
                Auto {autoRefresh ? 'ON' : 'OFF'}
              </Badge>
            </Button>
          </div>
        </div>
        <CardDescription>
          Last updated: {lastUpdated.toLocaleTimeString()}
        </CardDescription>
      </CardHeader>
      
      <CardContent className="space-y-6">
        {/* Overall System Health */}
        <div className="flex items-center justify-between p-4 rounded-lg bg-gradient-to-r from-blue-50 to-purple-50">
          <div className="flex items-center space-x-3">
            {getStatusIcon(systemHealth.status)}
            <div>
              <p className="font-medium">System Health</p>
              <p className={`text-sm ${getStatusColor(systemHealth.status)}`}>
                {systemHealth.status.charAt(0).toUpperCase() + systemHealth.status.slice(1)}
              </p>
            </div>
          </div>
          <div className="text-right">
            <p className="text-sm font-medium">Uptime</p>
            <p className="text-sm text-muted-foreground">{formatUptime()}</p>
          </div>
        </div>

        {/* Key Metrics */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {/* Seat Utilization */}
          <div className="space-y-3">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-2">
                <Armchair className="h-4 w-4 text-blue-600" />
                <span className="text-sm font-medium">Seat Utilization</span>
              </div>
              <div className="flex items-center space-x-1">
                {getTrendIcon(stats.trends.utilizationChange)}
                <span className={`text-xs ${getTrendColor(stats.trends.utilizationChange)}`}>
                  {Math.abs(stats.trends.utilizationChange)}%
                </span>
              </div>
            </div>
            <Progress value={stats.seatUtilization} className="h-2" />
            <p className="text-xs text-muted-foreground">
              {stats.occupiedSeats} of {stats.totalSeats} seats occupied ({stats.seatUtilization}%)
            </p>
          </div>

          {/* Manager Seat Usage */}
          <div className="space-y-3">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-2">
                <Crown className="h-4 w-4 text-purple-600" />
                <span className="text-sm font-medium">Manager Seats</span>
              </div>
              <div className="flex items-center space-x-1">
                {getTrendIcon(stats.trends.seatsChange)}
                <span className={`text-xs ${getTrendColor(stats.trends.seatsChange)}`}>
                  {Math.abs(stats.trends.seatsChange)}
                </span>
              </div>
            </div>
            <Progress value={stats.managerSeatUsage} className="h-2" />
            <p className="text-xs text-muted-foreground">
              {stats.managerSeats} of 5 manager seats created ({stats.managerSeatUsage}%)
            </p>
          </div>
        </div>

        <Separator />

        {/* Detailed Stats */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div className="text-center">
            <div className="flex items-center justify-center space-x-1 mb-1">
              <Users className="h-4 w-4 text-green-600" />
              <span className="text-lg font-semibold">{stats.totalEmployees}</span>
            </div>
            <p className="text-xs text-muted-foreground">Total Employees</p>
          </div>
          
          <div className="text-center">
            <div className="flex items-center justify-center space-x-1 mb-1">
              <CheckCircle className="h-4 w-4 text-blue-600" />
              <span className="text-lg font-semibold">{stats.assignedEmployees}</span>
            </div>
            <p className="text-xs text-muted-foreground">Assigned</p>
          </div>
          
          <div className="text-center">
            <div className="flex items-center justify-center space-x-1 mb-1">
              <Clock className="h-4 w-4 text-orange-600" />
              <span className="text-lg font-semibold">{stats.queueSize}</span>
            </div>
            <p className="text-xs text-muted-foreground">In Queue</p>
          </div>
          
          <div className="text-center">
            <div className="flex items-center justify-center space-x-1 mb-1">
              <Armchair className="h-4 w-4 text-gray-600" />
              <span className="text-lg font-semibold">{stats.availableSeats}</span>
            </div>
            <p className="text-xs text-muted-foreground">Available Seats</p>
          </div>
        </div>

        {/* System Issues/Warnings */}
        {systemHealth.issues && systemHealth.issues.length > 0 && (
          <>
            <Separator />
            <div className="space-y-2">
              <h4 className="text-sm font-medium text-yellow-600">System Warnings</h4>
              {systemHealth.issues.map((issue, index) => (
                <div key={index} className="flex items-center space-x-2 text-sm text-yellow-700">
                  <AlertTriangle className="h-3 w-3" />
                  <span>{issue}</span>
                </div>
              ))}
            </div>
          </>
        )}

        {/* Quick Actions */}
        <div className="flex items-center justify-between pt-2">
          <div className="flex items-center space-x-4 text-xs text-muted-foreground">
            <span>Response: {systemHealth.responseTime}</span>
            <span>Queue Limit: 10</span>
            <span>Max Seats: 100</span>
          </div>
          
          <Badge 
            variant={stats.queueSize >= 8 ? "warning" : "success"} 
            className="text-xs"
          >
            {stats.queueSize >= 8 ? 'Queue High' : 'Queue Normal'}
          </Badge>
        </div>
      </CardContent>
    </Card>
  );
};

export default SystemStatusPanel;