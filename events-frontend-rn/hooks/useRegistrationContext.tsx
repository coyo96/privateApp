import { useContext } from 'react';
import { RegistrationContext } from '../context/RegistrationContext';

/**
 * @returns {RegistrationContext}
 * ```ts
 * const {
 * //state
 * email,
 * firstName,
 * lastName,
 * dateOfBirth,
 * primaryPhone,
 * gender,
 * address
 * 
 * //Methods
 * setEmail,
 * setFirstName,
 * setLastName,
 * setDateOfBirth,
 * setPrimaryPhone,
 * setGender,
 * setAddress
 * } = useRegistrationContext();
 * ```
 */
export default function useRegistrationContext(): RegistrationContext {
    return useContext(RegistrationContext);
}