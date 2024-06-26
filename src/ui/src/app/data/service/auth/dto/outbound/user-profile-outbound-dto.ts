import { AddressDTO } from '../duplex/address-dto';

export interface UserProfileOutboundDTO {
  email: string | null;
  firstName: string | null;
  lastName: string | null;
  password: string | null;
  address: AddressDTO | null;
  businessName: string | null;
  businessAddress: AddressDTO | null;

  authPassword: string;
  authPasswordConfirmation: string;
}
