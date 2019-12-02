import { Criteria } from './criteria.interface';

export interface Product {
    id: string;
    description: string;
    descriptionShort?: string;
    name: string;
    nameShort?: string;
    imageUrl: string;
    criteriaDtoList: Criteria[];
}
