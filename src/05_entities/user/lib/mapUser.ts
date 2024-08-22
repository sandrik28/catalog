import { UserDto } from "../api/types";
import { UserType } from "../model/types";

export function mapUser(dto: UserDto): UserType {
  return {
    id: dto.id,
    firstName: dto.firstName,
    lastName: dto.lastName,
    email: dto.email,
  }
}