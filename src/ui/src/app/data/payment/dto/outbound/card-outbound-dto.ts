import { AddressDTO } from 'src/app/data/service/auth/dto/duplex/address-dto';

export interface CardOutboundDTO {
  code: string;
  ccv: string;
  expirationDateTime: number;
  address: AddressDTO;
  firstName: string;
  lastName: string;
}
