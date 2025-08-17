import React, { useState, useEffect } from 'react';
import { employeeApi } from '../services/api';
import { Users, Clock, AlertCircle, Crown, User, RefreshCw } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Button } from './ui/button';
import toast from 'react-hot-toast';

const EmployeeQueue = ({ onEmployeeSelect, refreshTrigger }) => {
  const [queue, setQueue] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedEmployee, setSelectedEmployee] = useState(null);

  useEffect(() => {
    fetchQueue();
  }, [refreshTrigger]);

  const fetchQueue = async () => {
    try {
      setLoading(true);
      const response = await employeeApi.getQueue();
      if (response.data.success) {
        setQueue(response.data.data);
      } else {
        toast.error('Failed to fetch employee queue');
      }
    } catch (error) {
      toast.error('Error fetching employee queue');
    } finally {
      setLoading(false);
    }
  };

  const handleEmployeeClick = (employee) => {
    setSelectedEmployee(employee);
    if (onEmployeeSelect) {
      onEmployeeSelect(employee);
    }
  };

  const getQueuePosition = (index) => {
    return index + 1;
  };

  const getDesignationBadgeColor = (isManager) => {
    return isManager ? 'bg-purple-100 text-purple-800' : 'bg-blue-100 text-blue-800';
  };

  if (loading) {
    return (
      <Card className="glass-card h-full">
        <CardHeader>
          <CardTitle className="flex items-center space-x-2">
            <Users className="h-5 w-5" />
            <span>Employee Queue</span>
          </CardTitle>
        </CardHeader>
        <CardContent className="flex justify-center py-8">
          <div className="loading-spinner h-8 w-8"></div>
        </CardContent>
      </Card>
    );
  }

  return (
    <Card className="glass-card h-full">
      <CardHeader>
        <div className="flex items-center justify-between">
          <CardTitle className="flex items-center space-x-2">
            <Users className="h-5 w-5" />
            <span>Employee Queue</span>
          </CardTitle>
          <Badge variant="outline">{queue.length}/10</Badge>
        </div>
        <CardDescription>
          Employees waiting for seat assignment
        </CardDescription>
        <Button
          variant="ghost"
          size="sm"
          onClick={fetchQueue}
          className="w-fit"
        >
          <RefreshCw className="h-4 w-4 mr-2" />
          Refresh
        </Button>
      </CardHeader>
      
      <CardContent>
        {queue.length === 0 ? (
          <div className="text-center py-8">
            <Clock className="h-12 w-12 text-muted-foreground mx-auto mb-3 opacity-50" />
            <p className="text-muted-foreground">No employees waiting for seat assignment</p>
          </div>
        ) : (
          <div className="space-y-3">
            <div className="flex items-center space-x-2 mb-3 p-3 rounded-lg bg-gradient-to-r from-yellow-50 to-orange-50 border border-yellow-200">
              <AlertCircle className="h-4 w-4 text-yellow-600" />
              <p className="text-sm text-yellow-800">
                Queue order determines assignment priority
              </p>
            </div>
            
            <div className="space-y-2 custom-scrollbar max-h-[500px] overflow-y-auto">
              {queue.map((employee, index) => (
                <div
                  key={employee.employeeId}
                  className={`border rounded-lg p-4 cursor-pointer transition-all duration-200 ${
                    selectedEmployee?.employeeId === employee.employeeId
                      ? 'ring-2 ring-primary bg-primary/10 border-primary'
                      : 'border-border hover:border-accent-foreground hover:bg-accent/50'
                  }`}
                  onClick={() => handleEmployeeClick(employee)}
                >
                  <div className="flex items-center justify-between">
                    <div className="flex items-center space-x-3">
                      <div className="flex-shrink-0">
                        <div className={`w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold ${
                          index === 0 
                            ? 'bg-green-100 text-green-800 ring-2 ring-green-300' 
                            : 'bg-muted text-muted-foreground'
                        }`}>
                          {getQueuePosition(index)}
                        </div>
                      </div>
                      <div className="p-2 rounded-full bg-primary/10">
                        {employee.designation?.isManager ? (
                          <Crown className="h-4 w-4 text-purple-600" />
                        ) : (
                          <User className="h-4 w-4 text-blue-600" />
                        )}
                      </div>
                      <div className="flex-1 min-w-0">
                        <h4 className="text-sm font-medium truncate">{employee.name}</h4>
                        <p className="text-xs text-muted-foreground">{employee.employeeId}</p>
                      </div>
                    </div>
                    <div className="flex items-center space-x-2">
                      <Badge 
                        variant={employee.designation?.isManager ? "default" : "secondary"}
                        className="text-xs"
                      >
                        {employee.designation?.designation}
                      </Badge>
                      {index === 0 && (
                        <Badge variant="success" className="text-xs">
                          Next
                        </Badge>
                      )}
                    </div>
                  </div>
                  
                  {index === 0 && (
                    <div className="mt-3 pt-3 border-t border-border">
                      <p className="text-xs text-green-700 font-medium flex items-center space-x-1">
                        <div className="w-2 h-2 rounded-full bg-green-500"></div>
                        <span>Ready for seat assignment</span>
                      </p>
                    </div>
                  )}
                </div>
              ))}
            </div>
            
            {queue.length === 10 && (
              <div className="text-center py-2 mt-3">
                <Badge variant="outline" className="text-xs">
                  Queue at maximum capacity (10/10)
                </Badge>
              </div>
            )}
          </div>
        )}
      </CardContent>
    </Card>
  );
};

export default EmployeeQueue;