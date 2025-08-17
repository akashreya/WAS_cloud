import axios from 'axios';

// Use environment variable or fall back to development URL
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 second timeout
});

// Add request interceptor for debugging
api.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Designations API
export const designationApi = {
  getAll: () => api.get('/designations'),
  getManagers: () => api.get('/designations/managers'),
  getNonManagers: () => api.get('/designations/non-managers'),
};

// Employees API
export const employeeApi = {
  getAll: () => api.get('/employees'),
  getById: (id) => api.get(`/employees/${id}`),
  getUnassigned: () => api.get('/employees/unassigned'),
  getAssigned: () => api.get('/employees/assigned'),
  getQueue: () => api.get('/employees/queue'),
  search: (name) => api.get(`/employees/search?name=${encodeURIComponent(name)}`),
  create: (data) => api.post('/employees', data),
  update: (id, data) => api.put(`/employees/${id}`, data),
  delete: (id) => api.delete(`/employees/${id}`),
};

// Seats API
export const seatApi = {
  getAll: () => api.get('/seats'),
  getById: (seatNumber) => api.get(`/seats/${seatNumber}`),
  getAvailable: () => api.get('/seats/available'),
  getOccupied: () => api.get('/seats/occupied'),
  getManagerSeats: () => api.get('/seats/manager-seats'),
  getAvailableManagerSeats: () => api.get('/seats/available-manager-seats'),
  create: (data) => api.post('/seats', data),
  delete: (seatNumber) => api.delete(`/seats/${seatNumber}`),
  assign: (data) => api.post('/seats/assign', data),
  unassign: (seatNumber) => api.post(`/seats/${seatNumber}/unassign`),
  reassign: (fromSeat, toSeat) => api.post(`/seats/reassign?fromSeat=${fromSeat}&toSeat=${toSeat}`),
  swap: (seat1, seat2) => api.post(`/seats/swap?seat1=${seat1}&seat2=${seat2}`),
  // New employee-centric operations
  reassignEmployee: (data) => api.post(`/seats/reassign?employeeId=${data.employeeId}&toSeat=${data.toSeat}`),
  swapEmployees: (data) => api.post(`/seats/swap-employees?employee1=${data.employee1}&employee2=${data.employee2}`),
};

export default api;