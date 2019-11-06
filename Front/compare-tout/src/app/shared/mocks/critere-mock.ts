import { Criteria } from '../models/criteria.interface';

export const criteriaMock1: Criteria[] = [
    {
        isMandatory: true,
        name: 'Prix',
        type: 'Float',
        unit: '€',
        value: '80',
    },
    {
        isMandatory: false,
        name: 'Dimensions',
        type: 'String',
        unit: 'cm',
        value: '1000 * 500',
    },
];
export const criteriaMock2: Criteria[] = [
    {
        isMandatory: true,
        name: 'Prix',
        type: 'Float',
        unit: '€',
        value: '1200',
    },
    {
        isMandatory: false,
        name: 'Durée de batterie',
        type: 'Int',
        unit: 'heures',
        value: '10',
    },
];
