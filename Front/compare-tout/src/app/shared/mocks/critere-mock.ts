import { Criteria } from '../models/criteria.interface';

export const criteriaMock1: Criteria[] = [
    {
        isMandatory: true,
        name: 'Prix',
        type: 'Float',
        unit: '€',
        value: '180',
    },
    {
        isMandatory: true,
        name: 'Marque',
        type: 'String',
        value: 'TRIGANO',
    },
    {
        isMandatory: true,
        name: 'Tranche d\'âge',
        type: 'String',
        value: '6 ans et +',
    },
    {
        isMandatory: false,
        name: 'Dimensions',
        type: 'String',
        unit: 'cm',
        value: '115/121 * 263',
    },
    {
        isMandatory: true,
        name: 'Couleur',
        type: 'String',
        value: 'Vert',
    },
];
export const criteriaMock2: Criteria[] = [
    {
        isMandatory: true,
        name: 'Prix',
        type: 'Float',
        unit: '€',
        value: '55',
    },
    {
        isMandatory: true,
        name: 'Marque',
        type: 'String',
        value: 'SMOBY',
    },
    {
        isMandatory: true,
        name: 'Tranche d\'âge',
        type: 'String',
        value: '0 - 3 ans',
    },
    {
        isMandatory: false,
        name: 'Dimensions',
        type: 'String',
        unit: 'cm',
        value: '56 * 137',
    },
    {
        isMandatory: true,
        name: 'Couleur',
        type: 'String',
        value: 'Bleu',
    },
];
