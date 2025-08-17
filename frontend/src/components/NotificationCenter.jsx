import React, { useState, useEffect } from 'react';
import { 
  Bell, 
  X, 
  CheckCircle, 
  AlertTriangle, 
  Info, 
  Clock,
  Trash2,
  Settings
} from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from './ui/card';
import { Badge } from './ui/badge';
import { Button } from './ui/button';
import { Separator } from './ui/separator';
import toast from 'react-hot-toast';

const NotificationCenter = () => {
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [isOpen, setIsOpen] = useState(false);
  const [settings, setSettings] = useState({
    showCapacityWarnings: true,
    showBusinessRuleAlerts: true,
    showMaintenanceNotices: true,
    showCriticalErrors: true,
    soundEnabled: true,
    emailNotifications: false
  });

  useEffect(() => {
    // Load notifications from localStorage
    const savedNotifications = JSON.parse(localStorage.getItem('notifications') || '[]');
    setNotifications(savedNotifications);
    setUnreadCount(savedNotifications.filter(n => !n.read).length);

    // Load settings
    const savedSettings = JSON.parse(localStorage.getItem('notificationSettings') || '{}');
    setSettings(prev => ({ ...prev, ...savedSettings }));

    // Real-time notifications would be handled via WebSocket/SSE in production
  }, []);

  const generateSampleNotification = () => {
    const systemAlerts = [
      {
        type: 'capacity_warning',
        title: 'High Capacity Alert',
        message: 'Seat utilization is approaching maximum capacity (90%)',
        severity: 'warning',
        timestamp: new Date().toISOString()
      },
      {
        type: 'manager_seats_full',
        title: 'Manager Seats Unavailable',
        message: 'All 5 manager seats are now occupied. New manager employees cannot be assigned.',
        severity: 'error',
        timestamp: new Date().toISOString()
      },
      {
        type: 'queue_limit',
        title: 'Queue Limit Reached',
        message: 'Employee queue has reached maximum limit of 10. New employees will need to wait.',
        severity: 'warning',
        timestamp: new Date().toISOString()
      },
      {
        type: 'system_maintenance',
        title: 'Scheduled Maintenance',
        message: 'System maintenance scheduled for tonight at 2:00 AM. Expected downtime: 30 minutes.',
        severity: 'info',
        timestamp: new Date().toISOString()
      },
      {
        type: 'business_rule_violation',
        title: 'Business Rule Alert',
        message: 'Multiple assignment attempts detected that violate manager seat requirements.',
        severity: 'warning',
        timestamp: new Date().toISOString()
      }
    ];

    const randomAlert = systemAlerts[Math.floor(Math.random() * systemAlerts.length)];
    addNotification(randomAlert);
  };

  const addNotification = (notification) => {
    const newNotification = {
      id: Date.now(),
      ...notification,
      read: false,
      timestamp: notification.timestamp || new Date().toISOString()
    };

    setNotifications(prev => {
      const updated = [newNotification, ...prev].slice(0, 50); // Keep only last 50
      localStorage.setItem('notifications', JSON.stringify(updated));
      return updated;
    });

    setUnreadCount(prev => prev + 1);

    // Show toast only for critical system alerts
    const shouldShowToast = 
      (settings.showCriticalErrors && notification.severity === 'error') ||
      (settings.showCapacityWarnings && notification.type === 'capacity_warning') ||
      (settings.showBusinessRuleAlerts && notification.type === 'business_rule_violation') ||
      (settings.showMaintenanceNotices && notification.type === 'system_maintenance');

    if (shouldShowToast) {
      const toastOptions = {
        duration: notification.severity === 'error' ? 6000 : 4000,
        icon: getNotificationIcon(notification.severity),
      };

      switch (notification.severity) {
        case 'warning':
          toast.error(notification.title, toastOptions);
          break;
        case 'error':
          toast.error(notification.title, toastOptions);
          break;
        default:
          toast(notification.title, toastOptions);
      }
    }
  };

  const markAsRead = (id) => {
    setNotifications(prev => {
      const updated = prev.map(n => n.id === id ? { ...n, read: true } : n);
      localStorage.setItem('notifications', JSON.stringify(updated));
      return updated;
    });
    setUnreadCount(prev => Math.max(0, prev - 1));
  };

  const markAllAsRead = () => {
    setNotifications(prev => {
      const updated = prev.map(n => ({ ...n, read: true }));
      localStorage.setItem('notifications', JSON.stringify(updated));
      return updated;
    });
    setUnreadCount(0);
  };

  const deleteNotification = (id) => {
    setNotifications(prev => {
      const notification = prev.find(n => n.id === id);
      const updated = prev.filter(n => n.id !== id);
      localStorage.setItem('notifications', JSON.stringify(updated));
      
      if (!notification?.read) {
        setUnreadCount(prev => Math.max(0, prev - 1));
      }
      
      return updated;
    });
  };

  const clearAllNotifications = () => {
    setNotifications([]);
    setUnreadCount(0);
    localStorage.removeItem('notifications');
    toast.success('All notifications cleared');
  };

  const getNotificationIcon = (severity) => {
    switch (severity) {
      case 'success':
        return <CheckCircle className="h-4 w-4 text-green-600" />;
      case 'warning':
        return <AlertTriangle className="h-4 w-4 text-yellow-600" />;
      case 'error':
        return <AlertTriangle className="h-4 w-4 text-red-600" />;
      default:
        return <Info className="h-4 w-4 text-blue-600" />;
    }
  };

  const getNotificationBadgeVariant = (type) => {
    switch (type) {
      case 'capacity_warning':
      case 'queue_limit':
        return 'warning';
      case 'manager_seats_full':
      case 'business_rule_violation':
        return 'destructive';
      case 'system_maintenance':
        return 'secondary';
      default:
        return 'outline';
    }
  };

  const getNotificationTypeLabel = (type) => {
    switch (type) {
      case 'capacity_warning':
        return 'Capacity';
      case 'manager_seats_full':
        return 'Manager Seats';
      case 'queue_limit':
        return 'Queue';
      case 'system_maintenance':
        return 'Maintenance';
      case 'business_rule_violation':
        return 'Rule Alert';
      default:
        return 'System';
    }
  };

  const formatRelativeTime = (timestamp) => {
    const now = new Date();
    const notificationTime = new Date(timestamp);
    const diffMs = now - notificationTime;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);

    if (diffMins < 1) return 'Just now';
    if (diffMins < 60) return `${diffMins}m ago`;
    if (diffHours < 24) return `${diffHours}h ago`;
    if (diffDays < 7) return `${diffDays}d ago`;
    return notificationTime.toLocaleDateString();
  };

  return (
    <div className="relative">
      <Button
        variant="ghost"
        size="sm"
        onClick={() => setIsOpen(!isOpen)}
        className="relative"
      >
        <Bell className="h-5 w-5" />
        {unreadCount > 0 && (
          <Badge
            variant="destructive"
            className="absolute -top-1 -right-1 h-5 w-5 rounded-full p-0 text-xs flex items-center justify-center"
          >
            {unreadCount > 99 ? '99+' : unreadCount}
          </Badge>
        )}
      </Button>

      {isOpen && (
        <Card className="absolute top-full right-0 mt-2 w-96 z-50 glass-card max-h-96 overflow-hidden">
          <CardHeader className="pb-3">
            <div className="flex items-center justify-between">
              <CardTitle className="text-lg">Notifications</CardTitle>
              <div className="flex items-center space-x-2">
                {notifications.length > 0 && (
                  <Button variant="ghost" size="sm" onClick={markAllAsRead} className="text-xs">
                    Mark all read
                  </Button>
                )}
                <Button variant="ghost" size="sm" onClick={() => setIsOpen(false)}>
                  <X className="h-4 w-4" />
                </Button>
              </div>
            </div>
            {unreadCount > 0 && (
              <CardDescription>
                You have {unreadCount} unread notification{unreadCount !== 1 ? 's' : ''}
              </CardDescription>
            )}
          </CardHeader>

          <CardContent className="p-0">
            {notifications.length === 0 ? (
              <div className="p-6 text-center text-muted-foreground">
                <Bell className="h-12 w-12 mx-auto mb-3 opacity-50" />
                <p>No system alerts</p>
                <p className="text-xs mt-1">You'll see capacity warnings, business rule violations, and maintenance notices here</p>
              </div>
            ) : (
              <div className="max-h-64 overflow-y-auto custom-scrollbar">
                {notifications.map((notification, index) => (
                  <div key={notification.id}>
                    <div
                      className={`p-4 hover:bg-accent cursor-pointer transition-colors ${
                        !notification.read ? 'bg-primary/5 border-l-4 border-l-primary' : ''
                      }`}
                      onClick={() => !notification.read && markAsRead(notification.id)}
                    >
                      <div className="flex items-start space-x-3">
                        <div className="mt-1">
                          {getNotificationIcon(notification.severity)}
                        </div>
                        <div className="flex-1 min-w-0">
                          <div className="flex items-start justify-between">
                            <div className="flex-1">
                              <p className="font-medium text-sm">{notification.title}</p>
                              <p className="text-xs text-muted-foreground mt-1">
                                {notification.message}
                              </p>
                              <div className="flex items-center space-x-2 mt-2">
                                <Badge
                                  variant={getNotificationBadgeVariant(notification.type)}
                                  className="text-xs"
                                >
                                  {getNotificationTypeLabel(notification.type)}
                                </Badge>
                                <div className="flex items-center space-x-1 text-xs text-muted-foreground">
                                  <Clock className="h-3 w-3" />
                                  <span>{formatRelativeTime(notification.timestamp)}</span>
                                </div>
                              </div>
                            </div>
                            <Button
                              variant="ghost"
                              size="sm"
                              onClick={(e) => {
                                e.stopPropagation();
                                deleteNotification(notification.id);
                              }}
                              className="h-6 w-6 p-0 opacity-0 group-hover:opacity-100 transition-opacity"
                            >
                              <Trash2 className="h-3 w-3" />
                            </Button>
                          </div>
                        </div>
                      </div>
                    </div>
                    {index < notifications.length - 1 && <Separator />}
                  </div>
                ))}
              </div>
            )}

            {notifications.length > 0 && (
              <>
                <Separator />
                <div className="p-3 bg-muted/50">
                  <div className="flex items-center justify-between">
                    <Button variant="ghost" size="sm" onClick={clearAllNotifications} className="text-xs">
                      <Trash2 className="h-3 w-3 mr-1" />
                      Clear all
                    </Button>
                    <Button variant="ghost" size="sm" className="text-xs">
                      <Settings className="h-3 w-3 mr-1" />
                      Settings
                    </Button>
                  </div>
                </div>
              </>
            )}
          </CardContent>
        </Card>
      )}
    </div>
  );
};

// Export functions to add system notifications from other components
export const addSystemNotification = (notification) => {
  const event = new CustomEvent('addNotification', { detail: notification });
  window.dispatchEvent(event);
};

// Helper functions to check system state and trigger notifications
export const checkCapacityWarning = (totalSeats, availableSeats) => {
  const utilizationRate = totalSeats > 0 ? ((totalSeats - availableSeats) / totalSeats) * 100 : 0;
  
  if (utilizationRate >= 90) {
    addSystemNotification({
      type: 'capacity_warning',
      title: 'Critical Capacity Alert',
      message: `Seat utilization is at ${Math.round(utilizationRate)}%. Only ${availableSeats} seats remaining.`,
      severity: 'error'
    });
  } else if (utilizationRate >= 80) {
    addSystemNotification({
      type: 'capacity_warning',
      title: 'High Capacity Warning',
      message: `Seat utilization is at ${Math.round(utilizationRate)}%. Consider adding more seats.`,
      severity: 'warning'
    });
  }
};

export const checkManagerSeatsAvailability = (availableManagerSeats, totalManagerSeats = 5) => {
  if (availableManagerSeats === 0) {
    addSystemNotification({
      type: 'manager_seats_full',
      title: 'Manager Seats Unavailable',
      message: `All ${totalManagerSeats} manager seats are occupied. New manager employees cannot be assigned.`,
      severity: 'error'
    });
  } else if (availableManagerSeats === 1) {
    addSystemNotification({
      type: 'manager_seats_full',
      title: 'Manager Seats Low',
      message: `Only ${availableManagerSeats} manager seat remaining out of ${totalManagerSeats}.`,
      severity: 'warning'
    });
  }
};

export const checkQueueLimit = (queueSize, maxQueue = 10) => {
  if (queueSize >= maxQueue) {
    addSystemNotification({
      type: 'queue_limit',
      title: 'Queue Limit Reached',
      message: `Employee queue has reached maximum limit of ${maxQueue}. New employees will need to wait.`,
      severity: 'warning'
    });
  }
};

export const notifyBusinessRuleViolation = (ruleType, message) => {
  addSystemNotification({
    type: 'business_rule_violation',
    title: 'Business Rule Violation',
    message: message,
    severity: 'warning'
  });
};

export default NotificationCenter;