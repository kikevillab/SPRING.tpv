/**
 * Created by fran lopez on 14/05/2017.
 */

import {InMemoryDbService} from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
    createDb() {
        let customers = [
            {
                mobile: 600000000,
                username: 'user1',
                dni: '66666666A',
                email: 'user1@gmail.com',
                address: 'address1',
                password: 'password1',
                active: true
            },
            {
                mobile: 600000001,
                username: 'user2',
                dni: '66666666B',
                email: 'user2@gmail.com',
                address: 'address2',
                password: 'password2',
                active: false
            },
            {
                mobile: 600000002,
                username: 'user3',
                dni: '66666666C',
                email: 'user3@gmail.com',
                address: 'address2',
                password: 'password2',
                active: false
            },
            {
                mobile: 600000003,
                username: 'user4',
                dni: '66666666D',
                email: 'user4@gmail.com',
                address: 'address3',
                password: 'password3',
                active: true
            },
            {
                mobile: 600000004,
                username: 'user5',
                dni: '66666666E',
                email: 'user5@gmail.com',
                address: 'address4',
                password: 'password4',
                active: false
            },
            {
                mobile: 600000005,
                username: 'user6',
                dni: '66666666F',
                email: 'user6@gmail.com',
                address: 'address5',
                password: 'password5',
                active: true
            },
            {
                mobile: 600000006,
                username: 'user7',
                dni: '66666666G',
                email: 'user7@gmail.com',
                address: 'address6',
                password: 'password6',
                active: true
            },
            {
                mobile: 600000007,
                username: 'user8',
                dni: '66666666H',
                email: 'user8@gmail.com',
                address: 'address7',
                password: 'password7',
                active: true
            },
            {
                mobile: 600000008,
                username: 'user9',
                dni: '66666666I',
                email: 'user9@gmail.com',
                address: 'address8',
                password: 'password8',
                active: true
            },
            {
                mobile: 600000009,
                username: 'user10',
                dni: '66666666J',
                email: 'user10@gmail.com',
                address: 'address9',
                password: 'password9',
                active: true
            },
            {
                mobile: 600000010,
                username: 'user11',
                dni: '66666666K',
                email: 'user11@gmail.com',
                address: 'address10',
                password: 'password10',
                active: true
            },
            {
                mobile: 600000011,
                username: 'user12',
                dni: '66666666L',
                email: 'user12@gmail.com',
                address: 'address10',
                password: 'password10',
                active: true
            },
            {
                mobile: 600000012,
                username: 'user13',
                dni: '66666666M',
                email: 'user13@gmail.com',
                address: 'address11',
                password: 'password11',
                active: true
            },
            {
                mobile: 600000013,
                username: 'user14',
                dni: '66666666N',
                email: 'user14@gmail.com',
                address: 'address12',
                password: 'password12',
                active: true
            },
            {
                mobile: 600000014,
                username: 'user15',
                dni: '66666666O',
                email: 'user15@gmail.com',
                address: 'address13',
                password: 'password13',
                active: true
            },
            {
                mobile: 600000015,
                username: 'user16',
                dni: '66666666P',
                email: 'user16@gmail.com',
                address: 'address14',
                password: 'password14',
                active: true
            },
        ];
        return {customers};
    }
}