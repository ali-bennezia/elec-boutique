import { AddressDTO } from '../duplex/address-dto';

export interface UserRegisterOutboundDTO {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  address: AddressDTO;
  businessName: string;
  businessAddress: AddressDTO;
  isProvider: boolean;
}
