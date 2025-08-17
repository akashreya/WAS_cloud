import React, { useState, useEffect } from 'react';
import { 
  Shield, 
  AlertTriangle, 
  Lock, 
  CheckCircle, 
  ArrowRight,
  Activity,
  Users,
  Armchair,
  Crown,
  RefreshCw,
  Info,
  X,
  AlertCircle
} from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Button } from './ui/button';
import { Progress } from './ui/progress';
import { Separator } from './ui/separator';
import { seatApi, employeeApi } from '../services/api';
import toast from 'react-hot-toast';

const BusinessRulesDashboard = () => {
  const [stats, setStats] = useState({
    totalSeats: 0,
    managerSeats: 0,
    maxManagers: 5,
    queueSize: 0,
    seatUtilization: 0,
    managerSeatUsage: 0,
    systemHealth: 'healthy'
  });
  
  const [loading, setLoading] = useState(true);
  const [lastUpdated, setLastUpdated] = useState(new Date());

  useEffect(() => {
    fetchSystemStats();
    const interval = setInterval(fetchSystemStats, 30000); // Refresh every 30 seconds
    return () => clearInterval(interval);
  }, []);

  const fetchSystemStats = async () => {
    try {
      const [
        allSeatsRes,
        occupiedSeatsRes,
        managerSeatsRes,
        unassignedEmployeesRes
      ] = await Promise.all([
        seatApi.getAll(),
        seatApi.getOccupied(),
        seatApi.getManagerSeats(),
        employeeApi.getUnassigned()
      ]);

      const totalSeats = allSeatsRes.data.data.length;
      const occupiedSeats = occupiedSeatsRes.data.data.length;
      const managerSeats = managerSeatsRes.data.data.length;
      const queueSize = unassignedEmployeesRes.data.data.length;

      setStats({
        totalSeats,
        managerSeats,
        maxManagers: 5,
        queueSize,
        seatUtilization: totalSeats > 0 ? Math.round((occupiedSeats / totalSeats) * 100) : 0,
        managerSeatUsage: managerSeats > 0 ? Math.round((managerSeats / 5) * 100) : 0,
        systemHealth: 'healthy'
      });
      
      setLastUpdated(new Date());
    } catch (error) {
      toast.error('Failed to load system statistics');
    } finally {
      setLoading(false);
    }
  };

  const businessRules = [
    {
      id: 'manager-seats',
      icon: Shield,
      title: 'Manager Seat Rules',
      description: 'Managers MUST use manager seats. Regular employees CANNOT use manager seats.',
      type: 'critical',
      code: 'BR-001'
    },
    {
      id: 'queue-system',
      icon: RefreshCw,
      title: 'Queue System',
      description: 'Only employees in the current queue (max 10) can be assigned to seats.',
      type: 'warning',
      code: 'BR-002'
    },
    {
      id: 'capacity-limits',
      icon: Lock,
      title: 'Capacity Limits',
      description: 'Maximum 100 seats total, 5 manager seats, 5 managers allowed.',
      type: 'info',
      code: 'BR-003'
    },
    {
      id: 'data-integrity',
      icon: CheckCircle,
      title: 'Data Integrity',
      description: 'Unique seat numbers, extension numbers, and one employee per seat.',
      type: 'success',
      code: 'BR-004'
    }
  ];

  const assignmentFlow = [
    { step: 1, title: 'Employee in Queue?', description: 'Check if employee is in current assignment queue' },
    { step: 2, title: 'Seat Available?', description: 'Verify seat is not occupied' },
    { step: 3, title: 'Type Match?', description: 'Manager seat ↔ Manager employee' },
    { step: 4, title: 'Assign Seat', description: 'Complete assignment & update queue' }
  ];

  const validationRules = [
    {
      category: 'Seat Format',
      rules: [
        'Pattern: WCP3-5F-###',
        'Max length: 11 characters',
        'Extension: 5 digits (60000-60099)'
      ]
    },
    {
      category: 'Employee Format',
      rules: [
        'ID Pattern: M#######',
        'Name: Letters & spaces only',
        'Max ID length: 8 characters'
      ]
    },
    {
      category: 'Business Logic',
      rules: [
        'One employee per seat',
        'Manager designation required',
        'Queue position priority'
      ]
    }
  ];

  const errorCodes = [
    {
      category: 'Seat Assignment (SA)',
      errors: [
        { code: 'Err-SA-001', message: 'Manager Seat cannot be assigned to Employee' },
        { code: 'Err-SA-002', message: 'Normal Seat cannot be assigned to Manager' },
        { code: 'Err-SA-007', message: 'Seat is already occupied' }
      ]
    },
    {
      category: 'Employee Management (ME)',
      errors: [
        { code: 'Err-ME-001', message: 'Already 5 managers present' },
        { code: 'Err-ME-002', message: 'Employee already exists' }
      ]
    },
    {
      category: 'Seat Management (MS)',
      errors: [
        { code: 'Err-MS-001', message: 'Already 5 Manager seats Present' },
        { code: 'Err-MS-002', message: 'Already 100 Seats Present' }
      ]
    }
  ];

  const getRuleVariant = (type) => {
    switch (type) {
      case 'critical': return 'destructive';
      case 'warning': return 'warning';
      case 'info': return 'info';
      case 'success': return 'success';
      default: return 'default';
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[400px]">
        <div className="loading-spinner h-12 w-12"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen glassmorphism-bg p-6">
      <div className="container mx-auto space-y-8">
        {/* Header */}
        <div className="text-center space-y-4">
          <div className="flex items-center justify-center space-x-3">
            <Activity className="h-8 w-8 text-primary" />
            <h1 className="text-4xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
              Workspace Allocation System
            </h1>
          </div>
          <p className="text-lg text-muted-foreground">Business Rules & System Overview</p>
          
          {/* System Stats Header */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mt-8">
            <Card className="glass-card">
              <CardContent className="p-6 text-center">
                <div className="flex items-center justify-center space-x-2 mb-2">
                  <Armchair className="h-5 w-5 text-blue-600" />
                  <span className="text-2xl font-bold">{stats.totalSeats}</span>
                </div>
                <p className="text-sm text-muted-foreground">Total Seats</p>
              </CardContent>
            </Card>
            
            <Card className="glass-card">
              <CardContent className="p-6 text-center">
                <div className="flex items-center justify-center space-x-2 mb-2">
                  <Crown className="h-5 w-5 text-purple-600" />
                  <span className="text-2xl font-bold">{stats.managerSeats}</span>
                </div>
                <p className="text-sm text-muted-foreground">Manager Seats</p>
              </CardContent>
            </Card>
            
            <Card className="glass-card">
              <CardContent className="p-6 text-center">
                <div className="flex items-center justify-center space-x-2 mb-2">
                  <Lock className="h-5 w-5 text-green-600" />
                  <span className="text-2xl font-bold">{stats.maxManagers}</span>
                </div>
                <p className="text-sm text-muted-foreground">Max Managers</p>
              </CardContent>
            </Card>
            
            <Card className="glass-card">
              <CardContent className="p-6 text-center">
                <div className="flex items-center justify-center space-x-2 mb-2">
                  <Users className="h-5 w-5 text-orange-600" />
                  <span className="text-2xl font-bold">{stats.queueSize}</span>
                </div>
                <p className="text-sm text-muted-foreground">Queue Size</p>
              </CardContent>
            </Card>
          </div>
        </div>

        {/* Core Business Rules */}
        <Card className="glass-card">
          <CardHeader>
            <CardTitle className="flex items-center space-x-2">
              <Info className="h-6 w-6" />
              <span>Core Business Rules</span>
            </CardTitle>
            <CardDescription>
              Essential rules governing the workspace allocation system
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {businessRules.map((rule) => {
                const Icon = rule.icon;
                return (
                  <div key={rule.id} className={`rule-card ${rule.type}`}>
                    <div className="flex items-start space-x-4">
                      <div className="p-2 rounded-lg bg-white/20">
                        <Icon className="h-6 w-6" />
                      </div>
                      <div className="flex-1">
                        <div className="flex items-center justify-between mb-2">
                          <h3 className="font-semibold">{rule.title}</h3>
                          <Badge variant={getRuleVariant(rule.type)} className="text-xs">
                            {rule.code}
                          </Badge>
                        </div>
                        <p className="text-sm text-muted-foreground">{rule.description}</p>
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
          </CardContent>
        </Card>

        {/* Seat Assignment Process */}
        <Card className="glass-card">
          <CardHeader>
            <CardTitle className="flex items-center space-x-2">
              <RefreshCw className="h-6 w-6" />
              <span>Seat Assignment Process</span>
            </CardTitle>
            <CardDescription>
              Step-by-step workflow for assigning seats to employees
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex flex-col md:flex-row items-center justify-between space-y-4 md:space-y-0 md:space-x-4">
              {assignmentFlow.map((step, index) => (
                <React.Fragment key={step.step}>
                  <div className="flex-1 text-center">
                    <div className="inline-flex items-center justify-center w-12 h-12 rounded-full bg-primary text-primary-foreground font-bold text-lg mb-3">
                      {step.step}
                    </div>
                    <h4 className="font-medium mb-2">{step.title}</h4>
                    <p className="text-sm text-muted-foreground">{step.description}</p>
                  </div>
                  {index < assignmentFlow.length - 1 && (
                    <ArrowRight className="h-6 w-6 text-muted-foreground hidden md:block" />
                  )}
                </React.Fragment>
              ))}
            </div>
          </CardContent>
        </Card>

        {/* Validation Rules and Error Codes */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Validation Rules */}
          <Card className="glass-card">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <CheckCircle className="h-6 w-6" />
                <span>Validation Rules</span>
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              {validationRules.map((category, index) => (
                <div key={index}>
                  <h3 className="font-medium mb-3">{category.category}</h3>
                  <ul className="space-y-2">
                    {category.rules.map((rule, ruleIndex) => (
                      <li key={ruleIndex} className="flex items-center space-x-2 text-sm">
                        <div className="w-2 h-2 rounded-full bg-primary"></div>
                        <span>{rule}</span>
                      </li>
                    ))}
                  </ul>
                  {index < validationRules.length - 1 && <Separator className="mt-4" />}
                </div>
              ))}
            </CardContent>
          </Card>

          {/* Error Codes */}
          <Card className="glass-card">
            <CardHeader>
              <CardTitle className="flex items-center space-x-2">
                <AlertTriangle className="h-6 w-6" />
                <span>Error Codes & Messages</span>
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-6">
              {errorCodes.map((category, index) => (
                <div key={index}>
                  <h3 className="font-medium mb-3">{category.category}</h3>
                  <div className="space-y-2">
                    {category.errors.map((error, errorIndex) => (
                      <div key={errorIndex} className="flex items-start space-x-3 p-2 rounded-lg bg-red-50 border border-red-200">
                        <Badge variant="destructive" className="text-xs mt-0.5">
                          {error.code}
                        </Badge>
                        <span className="text-sm text-red-800 flex-1">{error.message}</span>
                      </div>
                    ))}
                  </div>
                  {index < errorCodes.length - 1 && <Separator className="mt-4" />}
                </div>
              ))}
            </CardContent>
          </Card>
        </div>

        {/* System Status */}
        <Card className="glass-card">
          <CardHeader>
            <CardTitle className="flex items-center justify-between">
              <div className="flex items-center space-x-2">
                <Activity className="h-6 w-6" />
                <span>System Status Overview</span>
              </div>
              <Button variant="outline" size="sm" onClick={fetchSystemStats}>
                <RefreshCw className="h-4 w-4 mr-2" />
                Refresh
              </Button>
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="space-y-3">
                <h3 className="font-medium">Seat Utilization</h3>
                <Progress value={stats.seatUtilization} className="h-3" />
                <p className="text-sm text-muted-foreground">
                  {Math.round((stats.seatUtilization / 100) * stats.totalSeats)} of {stats.totalSeats} seats occupied
                </p>
              </div>
              
              <div className="space-y-3">
                <h3 className="font-medium">Manager Seat Usage</h3>
                <Progress value={stats.managerSeatUsage} className="h-3" />
                <p className="text-sm text-muted-foreground">
                  {stats.managerSeats} of 5 manager seats created
                </p>
              </div>
              
              <div className="space-y-3">
                <h3 className="font-medium">System Health</h3>
                <div className="flex items-center space-x-2">
                  <div className="w-3 h-3 rounded-full bg-green-500"></div>
                  <span className="text-sm font-medium">All systems operational</span>
                </div>
                <p className="text-sm text-muted-foreground">
                  Queue: {stats.queueSize} employees waiting
                </p>
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Footer */}
        <Card className="glass-card">
          <CardContent className="p-4 text-center text-sm text-muted-foreground">
            <p>Workspace Allocation System - Business Rules Dashboard</p>
            <p>Last updated: {lastUpdated.toLocaleString()}</p>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default BusinessRulesDashboard;