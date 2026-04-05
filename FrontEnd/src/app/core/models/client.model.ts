export interface Client {
  id: string;
  sharedKey: string;
  businessId: string;
  email: string;
  phone: string;
  startDate: string;
  endDate: string;
  dateAdded: string;
}

export interface CreateClientRequest {
  name: string;
  phone: string;
  email: string;
  startDate: string;
  endDate: string;
}

export interface AdvancedSearchParams {
  name?: string;
  email?: string;
  phone?: string;
  startDate?: string;
  endDate?: string;
}
