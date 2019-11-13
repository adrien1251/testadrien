import { Criteria } from './criteria.interface';

export interface Product {
    description: string;
    name: string;
    imageUrl: string;
    criteriaList: Criteria[];
}
